package edu.gmu.swe.gameproj.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import edu.gmu.swe.gameproj.jpa.User;
import edu.gmu.swe.gameproj.jpa.UserRole;
import edu.gmu.swe.gameproj.util.PasswordHelper;
import edu.gmu.swe.gameproj.util.SessionBeanHelper;
import edu.gmu.swe.gameproj.validator.UserValidator;

@Controller
@RequestMapping(value="/user/*")
public class UserController {
	protected final Log logger = LogFactory.getLog(getClass());

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
				
				if (gameProject.addUser(user)) {
					// Successfully added user.
					model.addAttribute("infoMessage", "Successfully added user " + user.getEmail());
					successfullyCreatedUser = Boolean.TRUE;					
				} else {
					// Couldn't add user
					model.addAttribute("errorMessage", 
							"A user with that e-mail has already registered in the Book Rating System.");
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
		if (user != null) {
			mav.addObject("user", user);
			mav.addObject("infoMessage", "Queried info for user " + userId);
		} else {
			mav.addObject("user", null);
			mav.addObject("errorMessage", "User " + userId + " does not exist.");			
		}

		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());
		
		mav.setViewName("UserInfo");
		
		return mav;
	}
	
	// Info Page for a group
	@RequestMapping(value="myProfile", method=RequestMethod.GET)
	public ModelAndView myProfile (HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();

		User user = SessionBeanHelper.getLoggedInUser();
		if (user != null) {
			mav.addObject("user", user);
			mav.addObject("infoMessage", "This is your personal profile.");
		} else {
			mav.addObject("user", null);
			mav.addObject("errorMessage", "Your profile could not be found in our database.");			
		}

		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());
		
		mav.setViewName("UserInfo");
		
		return mav;
	}

}
