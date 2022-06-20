package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.model.dao.impl.AccountDaoImpl;
import edu.demian.model.entity.Account;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
