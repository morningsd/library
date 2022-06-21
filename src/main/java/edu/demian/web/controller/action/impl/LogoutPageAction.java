package edu.demian.web.controller.action.impl;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@PageAccessor(allowedTo = {PageAccessorType.READER, PageAccessorType.LIBRARIAN, PageAccessorType.ADMINISTRATOR})
public final class LogoutPageAction extends Action {
    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}