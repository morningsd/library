package edu.demian.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public final class SessionLocaleFilter extends HttpFilter {

    @Override
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final String language = request.getParameter("language");
        if (language != null) {
            final HttpSession session = request.getSession();
            session.setAttribute("lang", language);
        }
        chain.doFilter(request, response);
    }

}
