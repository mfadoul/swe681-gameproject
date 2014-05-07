package edu.gmu.swe.gameproj.controller;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.Card;
import edu.gmu.swe.gameproj.jpa.CardEvent;
import edu.gmu.swe.gameproj.jpa.CardType;
import edu.gmu.swe.gameproj.jpa.GameState;
import edu.gmu.swe.gameproj.jpa.Player;
import edu.gmu.swe.gameproj.jpa.User;
import edu.gmu.swe.gameproj.mechanics.cards.action.Action;
import edu.gmu.swe.gameproj.mechanics.cards.action.ActionDto;
import edu.gmu.swe.gameproj.util.ActVm;
import edu.gmu.swe.gameproj.util.ActionDtoFactory;
import edu.gmu.swe.gameproj.util.ActionFactory;
import edu.gmu.swe.gameproj.util.SessionBeanHelper;

@Controller
@RequestMapping(value="/game/*")
public class GameController {
	protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="sessionRegistry")
    private SessionRegistryImpl sessionRegistry;


//	// This allows a user to play a game
//	@RequestMapping(value="play", method=RequestMethod.GET)
//	public ModelAndView play() {
//		ModelAndView mav = new ModelAndView();
//		
//		Player player = null;
//		
//		User user = SessionBeanHelper.getLoggedInUser();
//		mav.addObject("user", user);
//		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());  // Yep, it's the same as user...
//
//		GameProjectRemote gameProject = 
//				SessionBeanHelper.getGameProjectSessionBean();
//
//		player = gameProject.getActivePlayerByUser(user);
//
//		GameState gameState = gameProject.getActiveGameStateByUser(user);
//		
//		if (player == null || gameState == null) {
//			return new ModelAndView("redirect:/");//TODO Where to redirect them to
//		}
//		mav.addObject("actVm", new ActVm());
//		mav.addObject("player", player);
//		mav.addObject("gameState", gameState);
//		
//		mav.setViewName("Game_Play");
//		return mav;
//	}
	
	// This allows a user to play a game
		@RequestMapping(value="play", method=RequestMethod.GET)
		public ModelAndView play() {
			ModelAndView mav = new ModelAndView();
			
			Player player = null;
			
			// See if the user exists
			User user = SessionBeanHelper.getLoggedInUser();
			mav.addObject("user", user);
			mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());  // Yep, it's the same as user...
			
			// 1. If the user is not found, provide a message that it failed
			// 2. If the user isn't in a game, create a new game for them, and send them to the game.
			// 3. If the user is already in a game, send the user to their game
			
			if (user != null) {
				GameProjectRemote gameProject = 
						SessionBeanHelper.getGameProjectSessionBean();

				player = gameProject.getActivePlayerByUser(user);
				GameState gameState = null;
				if (player == null) {
					// 2. Create a game if there is no existing player.
					gameState = gameProject.createGameStateByUser(user);
					if (gameState != null) {
						// Now, create an instance of player
						player = gameProject.joinGameState(gameState.getId(), user.getId());
						if (player == null) {
							mav.addObject("errorMessage", "Couldn't create a new Player instance!");	
						} else {
							mav.addObject("player", player);
							mav.addObject("gameState", gameState);
							mav.addObject("actVm", new ActVm());
							mav.addObject("infoMessage", "Created a new Game!");
						}
					} else {
						// Something is busted.
						mav.addObject("errorMessage", "Couldn't create a new Game!");	
					}
					
				} else {
					// 3. If the user is already in a game, send the user to their game
					// Nothing to do...
					
					gameState = gameProject.getActiveGameStateByUser(user);
					mav.addObject("player", player);
					mav.addObject("gameState", gameState);
					mav.addObject("actVm", new ActVm());
					mav.addObject("infoMessage", "Existing game.");
				}
				
			} else {
				mav.addObject("errorMessage", "Your account could not be found in our database.");
				mav.addObject("player", null);
			}


			

			mav.setViewName("Game_Play");
			return mav;
		}
	
	@RequestMapping(value="play", method=RequestMethod.POST)
	public ModelAndView play(@Valid ActVm actVm, BindingResult result, Model model){
		boolean isValid = true;
		boolean isTurn = true;
		boolean isEnoughPlayers = true;
		boolean isActiveGame = true;
		GameState gameState = null;
		Player player = null;
		User user = SessionBeanHelper.getLoggedInUser();
		GameProjectRemote gameProject = SessionBeanHelper.getGameProjectSessionBean();
		ModelAndView mav = new ModelAndView();
		
		if(!result.hasErrors()){

			gameState = gameProject.getActiveGameStateByUser(user);
			player = gameProject.getActivePlayerByUser(user);
			String[] commandAry = actVm.getCommand().split(" ");
			

			//Precondition- THe validator has checked that the input is proper syntax
			//1. Check that it is this person's turn
			if(player.getTurn() != gameState.getTurn()){
				isTurn = false;

			}
			
			List<Player> players = gameProject.getPlayersByGameStateId(gameState.getId());
			if(players.size() != 2){
				isEnoughPlayers = false;
			}
			
			if(gameState.getCompleted() == 1){
				isActiveGame = false;
			}
			
			if(isTurn && isEnoughPlayers && isActiveGame){
			
			
				if(commandAry[0].equals("play")){
					//Check that in play phase. Can't play if not in this phase
					if(gameState.getPhase() != 1){
						isValid = false;
					}
					//2. Check that they have the card they wish to play in hand
					CardType cardType = CardType.getCardType(commandAry[1]);
					if(cardType != null) {
						if(player.getFirstInstanceInHandByType(cardType) == null){
							//Card not in hand
							isValid = false;
						}
					}

					else{
						//Not a valid card
						isValid = false;
					}
					
					if(player.getActionCount() < 1){
						isValid = false;
					}
					
					if(isValid){
						Action action = ActionFactory.buildCard(commandAry, gameProject);
						ActionDto dto = ActionDtoFactory.buildDto(commandAry, player);
						if(action != null && dto != null){
							try{
								action.act(dto);
							}
							catch(InvalidParameterException e){
								isValid = false;
							}
							catch(Exception e){
								//TODO This is an unrecoverable exception. Game probably should end
							}
						}
						else{
							isValid = false;
						}
					}
				}
				else if(commandAry[0].equals("buy")){
					CardType cardType = CardType.getCardType(commandAry[1]);
					Card cardToBuy = null;
					if(cardType == null){
						isValid = false;
					}
					else{
						//Verify player has enough money
						if(player.getCoinCount() < cardType.getCost()){
							isValid = false;
						}
						
						cardToBuy = gameState.getFirstInstanceInDeckByType(cardType);
						//Verify desired card is available
						if( cardToBuy == null){
							isValid = false;
						}
					}
					//Verify player has enough buys
					if(player.getBuyCount() < 1){
						isValid = false;
					}
					
					if(isValid){
						if(!gameProject.buy(player, cardToBuy, gameState)){
							isValid = false;
							//TODO: Do I need to do something more drastic here?
						}
						//TODO check for game over condition
						
					}
					
					//Make call
					
				}
				else if(commandAry[0].equals("done")){
					gameProject.endTurn(player, gameState);
				}
				else{
					isValid = false;
				}
				
				if(!isValid){
					mav.addObject("errorMessage", "Invalid input");
				}
			}
			else{
				if(!isActiveGame){
					mav.addObject("user", user);
					mav.addObject("loggedInUser", user); 
					mav.setViewName("UserInfo");
					return mav;
				}
				
				if(!isEnoughPlayers){
					mav.addObject("errorMessage", "Still waiting on another player");
				}
				else{
					mav.addObject("errorMessage", "Not your turn");
				}
			}
			
		}
		else{
			//model.addAllAttributes(result.getAllErrors());
			mav.addObject("errorMessage", "Invalid input");
		}
		

		//GameProjectRemote gameProject2 = SessionBeanHelper.getGameProjectSessionBean();
		User user2 = SessionBeanHelper.getLoggedInUser();
		GameState gameStateUpdated = gameProject.getActiveGameStateByUser(user2);
		Player playerUpdated = gameProject.getActivePlayerByUser(user2);
		
		if(gameProject.isWinner(gameStateUpdated)){
			gameProject.setWinnerEndGame(gameStateUpdated);
			mav.addObject("user", user);
			mav.addObject("loggedInUser", user); 
			mav.setViewName("UserInfo");
			return mav;
		}
		mav.addObject("user", user);
		mav.addObject("loggedInUser", user); 
		mav.addObject("player", playerUpdated);
		mav.addObject("gameState", gameStateUpdated);
		mav.addObject("actVm", new ActVm());
		mav.setViewName("Game_Play");
		return mav;
	}
	
	
	// This allows a user to play a game
	@RequestMapping(value="openGames", method=RequestMethod.GET)
	public ModelAndView openGames() {
		ModelAndView mav = new ModelAndView();
		
		User user = SessionBeanHelper.getLoggedInUser();
		mav.addObject("user", user);
		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());  // Yep, it's the same as user...

		if (user != null) {
			GameProjectRemote gameProject = 
					SessionBeanHelper.getGameProjectSessionBean();
			
			List<GameState> gameStates = gameProject.getOpenGames();
			mav.addObject("gameStates", gameStates);

			if (gameStates != null) {
				mav.addObject("infoMessage", "Found " + gameStates.size() + " open games.");
			} else {
				mav.addObject("errorMessage", "Failed to query for open games.");
			}
		} else {
			mav.addObject("errorMessage", "Your account could not be found in our database.");
		}
		mav.setViewName("Game_OpenGames");
		return mav;
	}
	
	// Attempt to join a game.
	@RequestMapping(value = "play/{gameStateId}", method = RequestMethod.GET)
	public ModelAndView info (@PathVariable("gameStateId") int gameStateId,
			HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();
		
		User user = SessionBeanHelper.getLoggedInUser();
		mav.addObject("user", user);
		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());  // Yep, it's the same as user...
		mav.addObject("actVm", new ActVm());
		if (user != null) {
			GameProjectRemote gameProject = 
					SessionBeanHelper.getGameProjectSessionBean();
			
			Player player = gameProject.joinGameState(gameStateId, user.getId());
			GameState gameState = gameProject.getGameStateById(gameStateId);
			mav.addObject("player", player);

			if (player == null) {
				mav.addObject("errorMessage", "Failed to join game.");
			} else {
				mav.addObject("gameState", gameState);
				mav.addObject("actVm", new ActVm());
				mav.addObject("infoMessage", "Joined game # " + player.getGameState().getId() + ".");
			}
		} else {
			mav.addObject("errorMessage", "Your account could not be found in our database.");
		}
		mav.setViewName("Game_Play");
		return mav;
	}
	
	// This allows a user to quit their current game
	@RequestMapping(value="quit", method=RequestMethod.GET)
	public ModelAndView quit() {
		ModelAndView mav = new ModelAndView();
		
		Player player = null;
		
		// See if the user exists
		User user = SessionBeanHelper.getLoggedInUser();
		mav.addObject("user", user);
		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());  // Yep, it's the same as user...
		
		// 1. If the user is not found, provide a message that it failed
		// 2. If the user isn't in a game, inform them that they are not currently playing.
		// 3. If the user is already in a game, quit the game
		
		if (user != null) {
			GameProjectRemote gameProject = 
					SessionBeanHelper.getGameProjectSessionBean();

			player = gameProject.getActivePlayerByUser(user);
			
			if (player != null) {
				mav.addObject("infoMessage", "You forfeited the game.");
				gameProject.forfeitActiveGameByUser(user);

			} else {
				mav.addObject("errorMessage", "You aren't playing a game!");
			} 
		} else {
			mav.addObject("errorMessage", "Your account could not be found in our database.");
			mav.addObject("player", null);
		}
		
		mav.setViewName("UserInfo");
		return mav;
	}

	// Attempt to join a game.
	@RequestMapping(value = "report/{gameStateId}", method = RequestMethod.GET)
	public ModelAndView report (@PathVariable("gameStateId") int gameStateId,
			HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();
		
		User user = SessionBeanHelper.getLoggedInUser();
		mav.addObject("user", user);
		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());  // Yep, it's the same as user...

		mav.addObject("title", "Report for Game " + gameStateId);

		if (user != null) {
			GameProjectRemote gameProject = 
					SessionBeanHelper.getGameProjectSessionBean();
			
			GameState gameState = gameProject.getGameStateById(gameStateId);

			mav.addObject("gameState", gameState);
			
			if (gameState != null) {
				// Must be completed!
				System.out.println("------ Number of cards in game = " + gameState.getCards().size());
				List<Player> players = gameProject.getPlayersByGameStateId(gameStateId);
				
				if (gameState.getCompleted() == 1)  { 
					List<CardEvent> cardEvents = gameProject.getCardEventsByGameState(gameState);
					mav.addObject("cardEvents", cardEvents);
					mav.addObject("players", players);
				} else {
					mav.addObject("errorMessage", "Game is in progress.  Report will be available when the game is done.");
					mav.addObject("cardEvents", null);
				}
			}
			
			if (gameState == null) {
				mav.addObject("errorMessage", "Game could not be found.");
			} else {
				mav.addObject("infoMessage", "Report for game # " + gameState.getId() + ".");
			}
		} else {
			mav.addObject("errorMessage", "Your account could not be found in our database.");
		}
		mav.setViewName("Game_Report");
		return mav;
	}

	// Attempt to join a game.
	@RequestMapping(value = "watch/{gameStateId}", method = RequestMethod.GET)
	public ModelAndView watch (@PathVariable("gameStateId") int gameStateId,
			HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();
		
		User user = SessionBeanHelper.getLoggedInUser();
		mav.addObject("user", user);
		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());  // Yep, it's the same as user...

		mav.addObject("title", "Watching Game " + gameStateId);

		if (user != null) {
			GameProjectRemote gameProject = 
					SessionBeanHelper.getGameProjectSessionBean();
			
			GameState gameState = gameProject.getGameStateById(gameStateId);

			mav.addObject("gameState", gameState);
			
			if (gameState != null) {
				// Should not be completed!
				System.out.println("------ Number of cards in game = " + gameState.getCards().size());
				List<Player> players = gameProject.getPlayersByGameStateId(gameStateId);
				
				if (gameState.getCompleted() != 1)  { 
					List<CardEvent> cardEvents = gameProject.getCardEventsByGameState(gameState);
					mav.addObject("cardEvents", cardEvents);
					mav.addObject("players", players);
				} else {
					mav.addObject("errorMessage", "Game is done.  You can look at the report for this game.");
					mav.addObject("cardEvents", null);
				}
			}
			
			if (gameState == null) {
				mav.addObject("errorMessage", "Game could not be found.");
			} else {
				mav.addObject("infoMessage", "Report for game # " + gameState.getId() + ".");
			}
		} else {
			mav.addObject("errorMessage", "Your account could not be found in our database.");
		}
		mav.setViewName("Game_Report");
		return mav;
	}

}
