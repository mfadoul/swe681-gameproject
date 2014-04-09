package edu.gmu.swe.gameproj.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

public class GameInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public void init(ServletConfig config){
        Enumeration<String> en = config.getInitParameterNames();
        Map<String, String> applicationProperties = new HashMap<String,String>();
        while (en.hasMoreElements()) {
        	String key = en.nextElement();
        	String value = config.getInitParameter(key);
        	applicationProperties.put(key, value);
        }
        ApplicationUtil.init(applicationProperties);
    }

}
