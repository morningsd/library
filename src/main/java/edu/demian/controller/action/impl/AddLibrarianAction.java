package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.AccountDAO;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Role;
import org.checkerframework.checker.units.qual.A;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddLibrarianAction extends Action {

    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return "/admin/addLibrarian";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setRoleId(Role.LIBRARIAN.ordinal());

        accountDAO.save(account, password);

        return "redirect:/admin/manageLibrarians";
    }
}
