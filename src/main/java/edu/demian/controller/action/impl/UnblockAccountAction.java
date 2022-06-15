package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.AccountDaoImpl;
import edu.demian.service.AccountService;
import edu.demian.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UnblockAccountAction extends Action {

    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support GET method");
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        final String accountId = request.getParameter("account_id");

        accountService.unblock(Long.parseLong(accountId));

        return "redirect:/admin/manageAccounts";
    }
}