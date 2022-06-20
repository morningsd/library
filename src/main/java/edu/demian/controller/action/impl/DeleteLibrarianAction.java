package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.AccountDaoImpl;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DeleteLibrarianAction extends Action {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support GET method");
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        final String librarianId = request.getParameter("librarian_id");

        accountService.delete(Long.parseLong(librarianId));

        return "redirect:/admin/manageLibrarians";
    }
}
