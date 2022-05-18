package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.AccountDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteLibrarianAction extends Action {

    private final AccountDaoImpl accountDAO = new AccountDaoImpl();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support GET method");
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String librarianId = request.getParameter("librarian_id");

        accountDAO.delete(Long.parseLong(librarianId));

        return "redirect:/admin/manageLibrarians";
    }
}
