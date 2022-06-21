package edu.demian.web.controller.page.jsp;

import edu.demian.model.entity.Account;
import edu.demian.model.entity.Role;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.web.exception.RedirectException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@PageAccessor(allowedTo = {PageAccessorType.ALL})
public class LoginPage {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {

    }

    private void login(final HttpServletRequest request) {
        final HttpSession session = request.getSession();

        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        final Account account = accountService.findByEmailAndPassword(email, password);

        if (account == null) {
            final String errorMessage = "Email or password is incorrect or your account is blocked. Contact the admin to solve this issue";
            request.setAttribute("errorMessage", errorMessage);
            //TODO
            throw new RuntimeException("Email or password is incorrect or your account is blocked. Contact the admin to solve this issue");
        }

        session.setAttribute("account", account);

        final Role accountRole = Role.getRole(account);
        session.setAttribute("accountRole", accountRole);
        session.setAttribute("isAdmin", account.getAdmin());

        throw new RedirectException("/jsp/home");
    }
}
