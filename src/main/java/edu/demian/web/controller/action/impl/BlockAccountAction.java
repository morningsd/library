package edu.demian.web.controller.action.impl;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.web.controller.action.ActionException;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;

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
