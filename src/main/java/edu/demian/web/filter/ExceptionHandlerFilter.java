package edu.demian.web.filter;

import edu.demian.web.exception.RedirectException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ExceptionHandlerFilter extends HttpFilter {

    @Override
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(request, response, chain);
        } catch (Exception e) {
            if (e instanceof RedirectException) {
                response.sendRedirect(((RedirectException) e).getTarget());
            } else {
                Throwable rootCause = e;
                while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
                    rootCause = rootCause.getCause();
                }
                String rootCauseMessage = rootCause.getMessage();
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", rootCauseMessage);
                response.sendRedirect("/jsp/error");
            }
        }
    }
}
