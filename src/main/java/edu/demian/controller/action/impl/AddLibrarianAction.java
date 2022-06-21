package edu.demian.controller.action.impl;

import edu.demian.annotation.PageAccessor;
import edu.demian.annotation.PageAccessorType;
import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Role;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@PageAccessor(allowedTo = {PageAccessorType.LIBRARIAN})
public final class AddLibrarianAction extends Action {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        return "/admin/addLibrarian";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        final String firstName = request.getParameter("fname");
        final String lastName = request.getParameter("lname");
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        final Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setRoleId(Role.LIBRARIAN.ordinal());

        accountService.saveLibrarian(account, password);

        return "redirect:/admin/manageLibrarians";
    }
}
