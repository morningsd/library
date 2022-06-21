package edu.demian.controller.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.demian.controller.action.impl.*;

public final class ActionFactory {
	
	private static final Map<String, Action> actions = new HashMap<>();

    static {
        actions.put("/", new HomePageAction());
    	actions.put("/jsp/home", new HomePageAction());
        actions.put("/jsp/login", new LoginPageAction());
        actions.put("/jsp/register", new RegisterPageAction());
        actions.put("/jsp/error", new ErrorPageAction());
        actions.put("/jsp/logout", new LogoutPageAction());
        actions.put("/jsp/catalog", new CatalogPageAction());
        actions.put("/jsp/cabinet", new CabinetPageAction());
        actions.put("/jsp/admin/addBook", new AddBookAction());
        actions.put("/jsp/admin/manageLibrarians", new ManageLibrariansAction());
        actions.put("/jsp/admin/addLibrarian", new AddLibrarianAction());
        actions.put("/jsp/admin/deleteLibrarian", new DeleteLibrarianAction());
        actions.put("/jsp/admin/manageAccounts", new ManageAccountAction());
        actions.put("/jsp/admin/blockAccount", new BlockAccountAction());
        actions.put("/jsp/admin/unblockAccount", new UnblockAccountAction());
        actions.put("/jsp/reader/orderBook", new OrderBookAction());
        actions.put("/jsp/librarian/readers", new ReadersPageAction());
        actions.put("/jsp/librarian/subscriptions", new SubscriptionsPageAction());
        actions.put("/jsp/librarian/orders", new OrdersPageAction());
        actions.put("/jsp/librarian/manageOrders", new ManageOrdersPageAction());
    }

    public static Action getAction(final HttpServletRequest request) {
		return actions.get(request.getRequestURI());
    }
}
