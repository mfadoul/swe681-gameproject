package edu.gmu.swe.gameproj.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.GameState;
import edu.gmu.swe.gameproj.jpa.User;
import edu.gmu.swe.gameproj.jpa.UserRole;
import edu.gmu.swe.gameproj.util.PasswordHelper;
import edu.gmu.swe.gameproj.util.SessionBeanHelper;
import edu.gmu.swe.gameproj.validator.UserValidator;

@Controller
@RequestMapping(value="/user/*")
public class UserController {
	protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

	@InitBinder
    protected void initBinder(WebDataBinder binder) {
		if (binder.getTarget() instanceof User) {			 
             binder.setValidator(new UserValidator());
		}
    }
	// This is when the user form is opened for the first time
	@RequestMapping(value="create", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView();
		GameProjectRemote gameProject = 
				SessionBeanHelper.getGameProjectSessionBean();
		Boolean successfullyCreatedUser = Boolean.FALSE;

		UserRole userRole = gameProject.getUserRoleById("reader");
		User user = new User();
		user.setUserRole(userRole);
        
		mav.addObject("user", user);
		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());
		mav.addObject("infoMessage", "Use this form to create a new user account.");
		mav.addObject("successfullyCreatedUser", successfullyCreatedUser);
		mav.setViewName("CreateUser");

		return mav;
	}
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	public String createUser(@Valid User user, BindingResult result, Model model) {
		Boolean successfullyCreatedUser = Boolean.FALSE;
		if (!result.hasErrors()) {
			try {
				// Attempt to add the user
				GameProjectRemote gameProject = 
						SessionBeanHelper.getGameProjectSessionBean();
				
				// Encrypt the password before persisting!
				user.setPassword(PasswordHelper.getEncryptedPassword(user.getPassword()));
				
				user.setAccountLocked((byte) 0); // 0 == the account is not locked
				if (gameProject.addUser(user)) {
					// Successfully added user.
					model.addAttribute("infoMessage", "Successfully added user " + user.getEmail());
					successfullyCreatedUser = Boolean.TRUE;					
				} else {
					// Couldn't add user
					model.addAttribute("errorMessage", 
							"A user with that e-mail has already registered in the Dominion Game Website.");
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			model.addAllAttributes(result.getAllErrors());
			model.addAttribute("user", user);
			model.addAttribute("errorMessage", "Unable to add user.  Please follow the guidance above.");
		}
		model.addAttribute("successfullyCreatedUser", successfullyCreatedUser);
		return "CreateUser";
	}
	
	// Info Page for a group
	@RequestMapping(value = "info/{userId}", method = RequestMethod.GET)
	public ModelAndView info (@PathVariable("userId") int userId,
			HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();
		GameProjectRemote gameProject = 
				SessionBeanHelper.getGameProjectSessionBean();

		User user = gameProject.getUserById(userId);
		
		User loggedInUser = SessionBeanHelper.getLoggedInUser();
		mav.addObject("loggedInUser", loggedInUser);

		if (user != null) {
			if ((loggedInUser != null) && (loggedInUser.getId()==user.getId())){
				mav.addObject("infoMessage", "This is your personal profile");
			} else {
				mav.addObject("infoMessage", "Queried info for user " + userId);
			}
			
			mav.addObject("user", user);
			
			Set<GameState> gamesWonList =  new HashSet<GameState> (gameProject.getGamesWonByUser(user));
			Set<GameState> gamesLostList = new HashSet<GameState> (gameProject.getGamesLostByUser(user));
			
			// Special info for the profile.
			mav.addObject("gamesWonCount", gamesWonList.size());
			mav.addObject("gamesLostCount", gamesLostList.size());

			mav.addObject("gamesWonList", gamesWonList);
			mav.addObject("gamesLostList", gamesLostList);

		} else {
			mav.addObject("user", null);
			mav.addObject("errorMessage", "User " + userId + " does not exist.");			
		}

		mav.setViewName("UserInfo");
		
		return mav;
	}
	
	// Info Page for a group
	@RequestMapping(value="myProfile", method=RequestMethod.GET)
	public ModelAndView myProfile (HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();
		GameProjectRemote gameProject = 
				SessionBeanHelper.getGameProjectSessionBean();

		User user = SessionBeanHelper.getLoggedInUser();
		
		User loggedInUser = SessionBeanHelper.getLoggedInUser();
		mav.addObject("loggedInUser", loggedInUser);

		if (user != null) {
			mav.addObject("user", user);
			mav.addObject("infoMessage", "This is your personal profile.");
			
			List<GameState> gamesWonList = gameProject.getGamesWonByUser(user);
			List<GameState> gamesLostList = gameProject.getGamesLostByUser(user);
			
			// Special info for the profile.
			mav.addObject("gamesWonCount", gamesWonList.size());
			mav.addObject("gamesLostCount", gamesLostList.size());

			mav.addObject("gamesWonList", gamesWonList);
			mav.addObject("gamesLostList", gamesLostList);
		} else {
			mav.addObject("user", null);
			mav.addObject("errorMessage", "Your profile could not be found in our database.");			
		}
		
		mav.setViewName("UserInfo");
		
		// Just for debug in the log.
		//this.printUsersLoggedIn();
		
		return mav;
	}

	// Display who is online
	@RequestMapping(value="activeUsers", method=RequestMethod.GET)
	public ModelAndView activeUsers (HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();
		//printUsersLoggedIn();

		User user = SessionBeanHelper.getLoggedInUser();
		if (user != null) {
			mav.addObject("user", user);
			mav.addObject("infoMessage", "This is the list of active users.");
		} else {
			mav.addObject("user", null);
			mav.addObject("errorMessage", "Your profile could not be found in our database.");			
		}

		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());
		
		// Find the users who are logged in:
		List<String> activeUsernames = this.getActiveUsernames();
		List<User> activeUsers = new ArrayList<User>();
		
		GameProjectRemote gameProject = 
				SessionBeanHelper.getGameProjectSessionBean();

		for (String username: activeUsernames) {
			User activeUser = gameProject.getUserByEmail(username);
			if (activeUser != null) {
				activeUsers.add(activeUser);
			}
		}
		
		mav.addObject("activeUsers", activeUsers);

		
//		// Remove the username of the current user.
//		Iterator<String> it = activeUsernames.iterator();
//		
//		while (it.hasNext()) {
//			String username = it.next();
//			if (username.equals(user.getEmail())) {
//				activeUsernames.remove(username);
//			}
//		}

		//mav.addObject("activeUsernames", activeUsernames);
		
		mav.setViewName("ActiveUsers");
		
		//this.printUsersLoggedIn();
		
		return mav;
	}

	// This is an example of accessing session information from Spring.
	// We'll be using something like this to figure out what other players
	// are online.
	@SuppressWarnings("unused")
	private String printUsersLoggedIn () {
		sessionRegistry.getAllPrincipals();
		System.out.println("------------");
		System.out.println("Received request to show users page");

		System.out.println("Total logged-in users: " + sessionRegistry.getAllPrincipals().size());
		System.out.println("List of logged-in users: ");
/*		for (Object principal: sessionRegistry.getAllPrincipals()) {
		    if (principal instanceof User) {
		    	System.out.println(" * " + ((User) principal).getUsername());
		    }
			//System.out.println(" * " + username);
		}*/
		
		for (Object username: sessionRegistry.getAllPrincipals()) {
			System.out.println(" * " + username);
			System.out.println("    * Package = " + username.getClass().getPackage());
			System.out.println("    * Class   = " + username.getClass().getName());
		}

		System.out.println("Total sessions including expired ones: " + sessionRegistry.getAllSessions(sessionRegistry.getAllPrincipals().get(0), true).size());
		System.out.println("Total sessions: " + sessionRegistry.getAllSessions(sessionRegistry.getAllPrincipals().get(0), false).size());
		System.out.println("------------");

		return "";
	}
	
	// Return a list of user names that have active sessions
	public List<String> getActiveUsernames() {
		List<String> activeUsernames = new ArrayList<String>();
		sessionRegistry.getAllPrincipals();

		for (Object principal: sessionRegistry.getAllPrincipals()) {
		    if (principal instanceof org.springframework.security.core.userdetails.User) {
		    	activeUsernames.add(
		    			((org.springframework.security.core.userdetails.User) principal).getUsername()
		    			);
		    } else {
		    	System.err.println ("Unexpected class type in UserController.getActiveUsernames()");
		    }
		}
		return activeUsernames;
	}
}
