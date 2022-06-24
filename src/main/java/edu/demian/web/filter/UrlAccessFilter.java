package edu.demian.web.filter;

import edu.demian.model.entity.Role;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.FrontServlet;
import edu.demian.web.exception.RedirectException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class UrlAccessFilter extends HttpFilter {

    @Override
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        FrontServlet.Route route = FrontServlet.Route.newRoute(request);
        String className = route.getClassName();

        Class<?> pageClass;
        try {
            pageClass = Class.forName(route.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RedirectException("/jsp/notFound");
        }


        final PageAccessor annotation = pageClass.getAnnotation(PageAccessor.class);
        final PageAccessorType[] pageAccessorTypes = annotation.allowedTo();

        final HttpSession session = request.getSession();
        final Role role = (Role) session.getAttribute("accountRole");
        final Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (role == null && presentAccessorType(pageAccessorTypes, PageAccessorType.NOT_LOGGED)) {
            super.doFilter(request, response, chain);
            return;
        }

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
            return;
        }

        throw new ServletException("You are not allowed to access this URL");
    }

    private boolean presentAccessorType(PageAccessorType[] types, PageAccessorType type) {
        return Arrays.asList(types).contains(type);
    }

}