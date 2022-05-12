package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.AccountDAO;
import edu.demian.model.entity.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ManageLibrariansAction extends Action {

    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        HttpSession session = request.getSession();

        List<Account> librarianList = accountDAO.findAllLibrarians();

        session.setAttribute("librarianList", librarianList);

        return "/admin/manageLibrarians";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}
