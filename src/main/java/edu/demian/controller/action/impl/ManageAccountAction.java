package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.AccountDaoImpl;
import edu.demian.model.entity.Account;
import edu.demian.service.AccountService;
import edu.demian.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public final class ManageAccountAction extends Action {

    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        final HttpSession session = request.getSession();

        final List<Account> accountList = accountService.findAll();

        session.setAttribute("accountList", accountList);

        return "/admin/manageAccounts";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}
