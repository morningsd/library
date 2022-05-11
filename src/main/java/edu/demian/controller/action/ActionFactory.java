package edu.demian.controller.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.demian.controller.action.impl.*;

public class ActionFactory {
	
	private static final Map<String, Action> actions = new HashMap<>();

    static {
    	actions.put("/home", new HomePageAction());
        actions.put("/login", new LoginPageAction());
        actions.put("/register", new RegisterPageAction());
        actions.put("/error", new ErrorPageAction());
        actions.put("/logout", new LogoutPageAction());
        actions.put("/catalog", new CatalogPageAction());
        actions.put("/cabinet", new CabinetPageAction());
    }

    public static Action getAction(HttpServletRequest request) {
		return actions.get(request.getPathInfo());
    }
}
