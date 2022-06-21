package edu.demian.controller.action.impl;

import edu.demian.annotation.PageAccessor;
import edu.demian.annotation.PageAccessorType;
import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@PageAccessor(allowedTo = PageAccessorType.ADMINISTRATOR)
public final class BlockAccountAction extends Action {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support GET method");
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        final String accountId = request.getParameter("account_id");

        accountService.block(Long.parseLong(accountId));

        return "redirect:/admin/manageAccounts";
    }
}
