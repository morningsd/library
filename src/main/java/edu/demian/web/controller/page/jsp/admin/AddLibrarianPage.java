package edu.demian.web.controller.page.jsp.admin;

import edu.demian.model.entity.Account;
import edu.demian.model.entity.Role;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;

@PageAccessor(allowedTo = {PageAccessorType.ADMINISTRATOR})
public class AddLibrarianPage {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {

    }


    private void addLibrarian(final HttpServletRequest request) {
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

        throw new RedirectException("/jsp/admin/manageLibrarians");
    }
}
