package edu.demian.web.controller.action.impl;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.web.controller.action.ActionException;
import edu.demian.model.entity.Account;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@PageAccessor(allowedTo = {PageAccessorType.ADMINISTRATOR})
public final class ManageLibrariansAction extends Action {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        final HttpSession session = request.getSession();

        final List<Account> librarianList = accountService.findAllLibrarians();

        session.setAttribute("librarianList", librarianList);

        return "/admin/manageLibrarians";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}
