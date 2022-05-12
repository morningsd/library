package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.AccountDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlockAccountAction extends Action {

    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support GET method");
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String accountId = request.getParameter("account_id");

        accountDAO.block(Long.parseLong(accountId));

        return "redirect:/admin/manageAccounts";
    }
}
