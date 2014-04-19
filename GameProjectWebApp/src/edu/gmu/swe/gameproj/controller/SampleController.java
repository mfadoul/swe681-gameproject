package edu.gmu.swe.gameproj.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.gmu.swe.gameproj.jpa.User;
import edu.gmu.swe.gameproj.util.SampleData;
import edu.gmu.swe.gameproj.util.SessionBeanHelper;
import edu.gmu.swe.gameproj.validator.SampleValidator;

@Controller
@RequestMapping(value="/sample/*")
public class SampleController {
	protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

	@InitBinder
    protected void initBinder(WebDataBinder binder) {
		if (binder.getTarget() instanceof SampleData) {			 
             binder.setValidator(new SampleValidator());
		}
    }
	// This is when the user form is opened for the first time
	@RequestMapping(value="sampleGet", method=RequestMethod.GET)
	public ModelAndView sampleGet() {
		ModelAndView mav = new ModelAndView();
		Boolean successfullyCreatedUser = Boolean.FALSE;
		System.out.println("Inside SampleController.sampleGet()");

		SampleData sampleData = new SampleData();
		sampleData.setBar("Default for Bar");
		sampleData.setFoo("Default for Foo");
		        
		mav.addObject("sampleData", sampleData);
		mav.addObject("loggedInUser", SessionBeanHelper.getLoggedInUser());
		mav.addObject("infoMessage", "Use this form to create a new user account.");
		mav.addObject("successfullyCreatedUser", successfullyCreatedUser);
		mav.setViewName("SampleGetView");

		System.out.println("Leaving SampleController.sampleGet()");
		return mav;
	}

	@RequestMapping(value="samplePost", method=RequestMethod.POST)
	public String samplePost(@Valid SampleData sampleData, BindingResult result, Model model) {
		Boolean successfullyCreatedUser = Boolean.FALSE;
		System.out.println("SampleController.samplePost()");
		if (!result.hasErrors()) {
			System.out.println("Did not detect errors.");
			model.addAttribute("successfullyCreatedUser", successfullyCreatedUser);
		} else {
			System.out.println("Detected errors.");
			model.addAllAttributes(result.getAllErrors());
			model.addAttribute("errorMessage", "Unable to add user.  Please follow the guidance above.");
			try {
				sampleData.setBar("Try again for Bar");
				sampleData.setFoo("Try again for Foo");
				        
				// Overriding the original values that were passed in via the "model" parameter.
				model.addAttribute("sampleData", sampleData);
				model.addAttribute("loggedInUser", SessionBeanHelper.getLoggedInUser());
				model.addAttribute("infoMessage", "Try to fill in foo-bar again.");
				model.addAttribute("successfullyCreatedUser", successfullyCreatedUser);

				model.addAttribute("errorMessage", 
					"SampleData failed validation.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "SampleGetView";
		}
		return "SamplePostView";
	}

}
