package edu.demian.web.controller.page.jsp.admin;

import edu.demian.model.entity.Account;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@PageAccessor(allowedTo = {PageAccessorType.ADMINISTRATOR})
public class ManageLibrariansPage {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final List<Account> librarianList = accountService.findAllLibrarians();
        session.setAttribute("librarianList", librarianList);
    }

    private void deleteLibrarian(final HttpServletRequest request) {
        String librarianIdStr = request.getParameter("librarian_id");

        long librarianId = Long.parseLong(librarianIdStr);

        accountService.delete(librarianId);

        throw new RedirectException("/jsp/admin/manageLibrarians");
    }
}
