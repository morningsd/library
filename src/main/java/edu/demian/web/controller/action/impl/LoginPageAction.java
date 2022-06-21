package edu.demian.web.controller.action.impl;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Role;
import edu.demian.model.service.AccountService;
import edu.demian.model.service.factory.ServiceFactory;
import edu.demian.model.service.factory.ServiceFactoryType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@PageAccessor(allowedTo = {PageAccessorType.ALL})
public final class LoginPageAction extends Action {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        return "/login";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        final HttpSession session = request.getSession();

        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        final Account account = accountService.findByEmailAndPassword(email, password);

        if (account == null) {
            final String errorMessage = "Email or password is incorrect or your account is blocked. Contact the admin to solve this issue";
            request.setAttribute("errorMessage", errorMessage);
            return "/error";
        }

        session.setAttribute("account", account);

        final Role accountRole = Role.getRole(account);
        session.setAttribute("accountRole", accountRole);
        session.setAttribute("isAdmin", account.getAdmin());

        return "redirect:/home";
    }
}
