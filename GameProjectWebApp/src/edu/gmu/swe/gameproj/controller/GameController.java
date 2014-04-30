package edu.gmu.swe.gameproj.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.GameState;
import edu.gmu.swe.gameproj.jpa.Player;
import edu.gmu.swe.gameproj.jpa.User;
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
			
			if (player == null) {
				// 2. Create a game if there is no existing player.
				GameState gameState = gameProject.createGameStateByUser(user);
				if (gameState != null) {
					// Now, create an instance of player
					player = gameProject.joinGameState(gameState.getId(), user.getId());
					if (player == null) {
						mav.addObject("errorMessage", "Couldn't create a new Player instance!");	
					} else {
						mav.addObject("infoMessage", "Created a new Game!");
					}
				} else {
					// Something is busted.
					mav.addObject("errorMessage", "Couldn't create a new Game!");	
				}
			} else {
				// 3. If the user is already in a game, send the user to their game
				// Nothing to do...
				mav.addObject("infoMessage", "Existing game.");
			}
			mav.addObject("player", player);
		} else {
			mav.addObject("errorMessage", "Your account could not be found in our database.");
			mav.addObject("player", null);
		}
		
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

//    @RequestMapping(value="action", method= RequestMethod.POST)
//    public ModelAndView action(@Valid ActionVm vm, BindingResult result, Model model) {
//        ModelAndView mav = new ModelAndView();
//        if(!result.hasErrors()){
//            //TODO Other validations, like does player have the card
//
//            Action playedCard = (Action)CardFactory.buildCard(vm.playedCardName);
//            ActionDto dto = new ActionDto();
//            dto.player = new Player();//TODO Replace with session player
//            dto.oldCardName = vm.oldCardName;
//            dto.newCardName = vm.newCardName;
//            dto.discardCardNames = (ArrayList<String>)Arrays.asList(vm.discardCardNames);
//
//            try {
//                playedCard.Act(dto);
//            }
//            catch(InvalidParameterException ex){
//                //TODO
//            }
//            catch(NullPointerException ex){
//                //TODO
//            }
//
//
//        }
//        else{
//            //TODO Add errors
//        }
//        //TODO set mav values
//
//        return mav;
//    }

}
