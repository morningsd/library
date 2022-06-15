package edu.demian.controller.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.demian.controller.action.impl.*;

public final class ActionFactory {
	
	private static final Map<String, Action> actions = new HashMap<>();

    static {
    	actions.put("/home", new HomePageAction());
        actions.put("/login", new LoginPageAction());
        actions.put("/register", new RegisterPageAction());
        actions.put("/error", new ErrorPageAction());
        actions.put("/logout", new LogoutPageAction());
        actions.put("/catalog", new CatalogPageAction());
        actions.put("/cabinet", new CabinetPageAction());
        actions.put("/admin/addBook", new AddBookAction());
        actions.put("/admin/manageLibrarians", new ManageLibrariansAction());
        actions.put("/admin/addLibrarian", new AddLibrarianAction());
        actions.put("/admin/deleteLibrarian", new DeleteLibrarianAction());
        actions.put("/admin/manageAccounts", new ManageAccountAction());
        actions.put("/admin/blockAccount", new BlockAccountAction());
        actions.put("/admin/unblockAccount", new UnblockAccountAction());
        actions.put("/reader/orderBook", new OrderBookAction());
    }

    public static Action getAction(final HttpServletRequest request) {
		return actions.get(request.getPathInfo());
    }
}
