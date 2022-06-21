package edu.demian.web.filter;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.web.controller.action.ActionFactory;
import edu.demian.model.entity.Role;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public final class UrlAccessFilter extends HttpFilter {

    @Override
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final Action action = ActionFactory.getAction(request);
        if (action == null) {
            throw new ServletException("You can't access given url :)");
        }

        PageAccessor annotation = action.getClass().getAnnotation(PageAccessor.class);
        PageAccessorType[] pageAccessorTypes = annotation.allowedTo();

        if (presentAccessorType(pageAccessorTypes, PageAccessorType.ALL)) {
            super.doFilter(request, response, chain);
            return;
        }

        final HttpSession session = request.getSession();
        final Role role = (Role) session.getAttribute("accountRole");
        final Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (role == null) {
            throw new ServletException("You are not allowed to access this URL");
        }

        if (role == Role.READER && presentAccessorType(pageAccessorTypes, PageAccessorType.READER)) {
            super.doFilter(request, response, chain);
            return;
        }

        if (role == Role.LIBRARIAN && presentAccessorType(pageAccessorTypes, PageAccessorType.LIBRARIAN)) {
            super.doFilter(request, response, chain);
            return;
        }

        if (isAdmin && presentAccessorType(pageAccessorTypes, PageAccessorType.ADMINISTRATOR)) {
            super.doFilter(request, response, chain);
        }

    }

    private boolean presentAccessorType(PageAccessorType[] types, PageAccessorType type) {
        return Arrays.asList(types).contains(type);
    }

}