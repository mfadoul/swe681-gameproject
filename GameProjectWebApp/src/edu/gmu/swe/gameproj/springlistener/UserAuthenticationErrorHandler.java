package edu.gmu.swe.gameproj.springlistener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationErrorHandler implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		System.out.println("**********************************");
		System.out.println("*        FAILED TO LOG IN        *");
		System.out.println("**********************************");
		
		Object userName = event.getAuthentication().getPrincipal();
		Object credentials = event.getAuthentication().getCredentials();
		System.err.println("Failed login using USERNAME " + userName);
		System.err.println("Failed login using PASSWORD " + credentials);	
	}

}

