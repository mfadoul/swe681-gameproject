package edu.gmu.swe.gameproj.util;

import java.util.Properties;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.security.core.context.SecurityContextHolder;

import edu.gmu.swe.gameproj.ejb.GameProjectRemote;
import edu.gmu.swe.gameproj.jpa.User;

public class SessionBeanHelper {
	public static GameProjectRemote getGameProjectSessionBean() {
		Context ctx;
		GameProjectRemote gameProject = null;
		//System.out.println("BEGIN TEST:");
		//System.out.println("-----------");
		//testEjbs();
		//System.out.println("-----------");
		
		try {
			Properties p = new Properties();
			ctx = new InitialContext(p);
			
			Object ejbRef = ctx.lookup
					("java:global/GameProjectEar/GameProjectEjb/GameProject!edu.gmu.swe.gameproj.ejb.GameProjectRemote");
			gameProject = (GameProjectRemote) ejbRef;			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gameProject;
	}
	
	// This is a test method to see which versions of the JNDI name can be found by the Context.
	public static void testEjbs () {
		Context ctx = null;
		GameProjectRemote gameProject = null;
		Properties p = new Properties();
		try {
			ctx = new InitialContext(p);
		} catch (NamingException e1) {
			System.out.println ("Failed to get initial context");
			e1.printStackTrace();
		}

		Vector<String> ejbNames = new Vector<String>();
		ejbNames.add("java:global/GameProjectEar/GameProjectEjb/GameProject!edu.gmu.swe.gameproj.ejb.GameProjectRemote");
		ejbNames.add("java:app/GameProjectEjb/GameProject!edu.gmu.swe.gameproj.ejb.GameProjectRemote");
		ejbNames.add("java:module/GameProject!edu.gmu.swe.gameproj.ejb.GameProjectRemote"); // FAIL
		ejbNames.add("java:jboss/exported/GameProjectEar/GameProjectEjb/GameProject!edu.gmu.swe.gameproj.ejb.GameProjectRemote");
		ejbNames.add("java:global/GameProjectEar/GameProjectEjb/GameProject!edu.gmu.swe.gameproj.ejb.GameProject");
		ejbNames.add("java:app/GameProjectEjb/GameProject!edu.gmu.swe.gameproj.ejb.GameProject");
		ejbNames.add("java:module/GameProject!edu.gmu.swe.gameproj.ejb.GameProject"); // FAIL

		for (String ejbName: ejbNames) {
			try {
				System.out.print ("Trying " + ejbName + ": ");
				Object ejbRef = ctx.lookup (ejbName);
				gameProject = (GameProjectRemote) ejbRef;
				System.out.println (((gameProject!=null)? "Found":"Not found"));

			} catch (NamingException e) {
				System.out.println (" Name Exception");
			} catch (Exception e) {
				System.out.println (" Exception");
			}
		}
	}

	// The username is actually the user's e-mail address.
	public static String getLoggedInUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	public static User getLoggedInUser() {
		GameProjectRemote gameProject = getGameProjectSessionBean();
		return gameProject.getUserByEmail(getLoggedInUsername());
	}

}
