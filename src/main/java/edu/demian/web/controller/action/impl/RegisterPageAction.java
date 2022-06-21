package edu.demian.web.controller.action.impl;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.model.entity.Account;
import edu.demian.model.service.AccountService;
import edu.demian.model.service.factory.ServiceFactory;
import edu.demian.model.service.factory.ServiceFactoryType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@PageAccessor(allowedTo = {PageAccessorType.ALL})
public final class RegisterPageAction extends Action {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        return "/register";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        final String firstName = request.getParameter("fname");
        final String lastName = request.getParameter("lname");
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        final Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);

        accountService.save(account, password);

        return "redirect:/login";
    }
}
