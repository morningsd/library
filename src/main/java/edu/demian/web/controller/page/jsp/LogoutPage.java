package edu.demian.web.controller.page.jsp;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@PageAccessor(allowedTo = {PageAccessorType.READER, PageAccessorType.LIBRARIAN, PageAccessorType.ADMINISTRATOR})
public class LogoutPage {

    private void action(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        throw new RedirectException("/jsp/login");
    }

}
