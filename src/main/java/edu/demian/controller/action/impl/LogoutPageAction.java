package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutPageAction extends Action {
    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}
