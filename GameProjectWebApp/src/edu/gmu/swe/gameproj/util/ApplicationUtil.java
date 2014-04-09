package edu.gmu.swe.gameproj.util;

import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ApplicationUtil {
	private static Map<String, String> applicationProperties;
	
	public static Object getEjbReference(String strEjbName) throws NamingException{
		Object oref =  null;
	    Context ic = null;
	    	    
	    // create an InitialContext, with JNDI properties
	    // the Context Factory listed below is used for CORBA
	    Properties p = new Properties();
	    ic = new InitialContext(p);

	    // Get the a reference to the Remote Object from JNDI
	    oref = ic.lookup(applicationProperties.get(strEjbName));
	    System.out.println("Client: Obtained a reference to EJB: " + oref.toString());

	    return oref;
	}

	public static void init(Map<String, String> applicationProps) {
		applicationProperties = applicationProps;		
	}
}
