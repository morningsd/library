package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.model.dao.impl.AccountDAO;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginPageAction extends Action {

    private final AccountDAO accountDAO = new AccountDAO();
    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        return "/login";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Account account = accountDAO.findByEmailAndPassword(email, password);

        if (account == null) {
            String errorMessage = "Email or password is incorrect";
            request.setAttribute("errorMessage", errorMessage);
            return "/error";
        }

        session.setAttribute("account", account);
        Role accountRole = Role.getRole(account);
        session.setAttribute("accountRole", accountRole);
        session.setAttribute("isAdmin", account.getAdmin());

        return "redirect:/home";
    }
}
