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


	// This allows a user to play a game
	@RequestMapping(value="play", method=RequestMethod.GET)
	public ModelAndView play() {
		ModelAndView mav = new ModelAndView();
		
		Player player = null;
		
		User user = SessionBeanHelper.getLoggedInUser();
		mav.addObject("user", user);
		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());  // Yep, it's the same as user...
		
		// 1. If the user is not found, provide a message that it failed
		// 2. If the user isn't in a game, create a new game for them, and send them to the game.
		// 3. If the user is already in a game, send the user to their game
		

		GameProjectRemote gameProject = 
				SessionBeanHelper.getGameProjectSessionBean();

		player = gameProject.getActivePlayerByUser(user);
		GameState gameState = gameProject.createGameStateByUser(user);
		
		if (player == null || gameState == null) {
			return new ModelAndView("redirect:/");//TODO Where to redirect them to
		}
			
		mav.addObject("player", player);
		mav.addObject("gameState", gameState);
		
		mav.setViewName("Game_Play");
		return mav;
	}
	
	@RequestMapping(value="play", method=RequestMethod.POST)
	public ModelAndView play(@Valid ActVm actVm, BindingResult result, Model model){
		boolean isValid = true;
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
				//TODO
			}
			
			//2. Check that they have the card they wish to play in hand
			//TODO this only works for Action
			ArrayList<Card> hand = player.getHand();
			Boolean hasCard = false;
			for(Card c : hand){
				if(c.getType().getName().equals(commandAry[1])); {
					hasCard = true;
					break;
				}
				
			}
			if(!hasCard){
				isValid = false;
			}

			//TODO need to validate that they are in the right phase.  IE, can't act after buy. No DB field for currently
			
			
			//Note: Card specific validations deferred to the cards
			if(commandAry[0].equals("act")){
				Action action = ActionFactory.buildCard(commandAry, gameProject);
				ActionDto dto = ActionDtoFactory.buildDto(commandAry);
				if(action != null && dto != null){
					try{
						action.act(dto);
					}
					catch(InvalidParameterException e){
						isValid = false;
					}
					catch(Exception e){
						//TODO This is an unrecoverable exception. Game needs to end
					}
				}
				else{
					isValid = false;
				}
			}
			else if(commandAry[0].equals("buy")){
				//TODO
			}
			else if(commandAry[0].equals("done")){
				//TODO
			}
			else{
				isValid = false;
			}
			
			
			
		}
		else{
			model.addAllAttributes(result.getAllErrors());
		}
		
		gameState = gameProject.getActiveGameStateByUser(user);
		player = gameProject.getActivePlayerByUser(user);
		mav.addObject("user", user);
		mav.addObject("loggedInUser", user); 
		mav.addObject("player", player);
		mav.addObject("gameState", gameState);
		
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

		if (user != null) {
			GameProjectRemote gameProject = 
					SessionBeanHelper.getGameProjectSessionBean();
			
			Player player = gameProject.joinGameState(gameStateId, user.getId());
			mav.addObject("player", player);

			if (player == null) {
				mav.addObject("errorMessage", "Failed to join game.");
			} else {
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



}
