package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.model.dao.impl.AccountDaoImpl;
import edu.demian.model.entity.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPageAction extends Action {

    private final AccountDaoImpl accountDAO = new AccountDaoImpl();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        return "/register";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);

        accountDAO.save(account, password);

        return "redirect:/login";
    }
}
