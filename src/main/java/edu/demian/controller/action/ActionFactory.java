package edu.demian.controller.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.demian.controller.action.impl.HomePageAction;

public class ActionFactory {
	
	private static final Map<String, Action> actions = new HashMap<>();

    static {
    	actions.put("/home", new HomePageAction());
    }

    public static Action getAction(HttpServletRequest request) {
		return actions.get(/* request.getMethod() + */request.getPathInfo());
    }
}
