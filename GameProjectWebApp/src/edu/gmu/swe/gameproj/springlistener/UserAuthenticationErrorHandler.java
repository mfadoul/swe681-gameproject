package edu.gmu.swe.gameproj.springlistener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.FailedLogin;
import edu.gmu.swe.gameproj.util.SessionBeanHelper;

@Component
public class UserAuthenticationErrorHandler implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		System.out.println("**********************************");
		System.out.println("*        FAILED TO LOG IN        *");
		System.out.println("**********************************");
		
		Object userName = event.getAuthentication().getPrincipal();
		System.err.println("Failed login using USERNAME " + userName);
		
		// Don't report the password to the console
		
		// Object credentials = event.getAuthentication().getCredentials();
		// System.err.println("Failed login using PASSWORD " + credentials);
		
        WebAuthenticationDetails wad = null;
        String userIpAddress         = null;
        //boolean isAuthenticatedByIP  = false;

        // Get the IP address of the user trying to use the site
        wad = (WebAuthenticationDetails) event.getAuthentication().getDetails();
        userIpAddress = wad.getRemoteAddress();

        System.err.println ("Failed User IP address = " + userIpAddress);
        
        // Log the failed login event:
        GameProjectRemote gameProject = SessionBeanHelper.getGameProjectSessionBean();
        if (userIpAddress != null) {
        	try {
	        	gameProject.registerFailedLogin(userName.toString());
	        	FailedLogin failedLogin = gameProject.getFailedLogin(userName.toString());
	        	System.err.println("Failed Login (" + failedLogin.getId() + ", " + 
	        			failedLogin.getLastFailAttemptDate() + ", " + 
	    	        			failedLogin.getDailyFailCount() + ", " + 
	    	        			failedLogin.getFailCount() + ").");	        	
        	} catch  (NullPointerException npe) {
    			System.err.println("Null Pointer Exception in UserAuthenticationErrorHandler."); // " + npe);
        	}
        	catch  (Exception e) {
    			// System.err.println("Exception: " + e);
        	}
        } else {
        	// Null pointer...
        	System.err.println("Null pointer exception trying to get the user IP address.");
        }
	}
}

