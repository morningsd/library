package edu.demian.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.demian.web.controller.action.Action;
import edu.demian.web.controller.action.ActionFactory;

public final class FrontServlet extends HttpServlet {


	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
		try {
            final Action action = ActionFactory.getAction(request);
            final String view = action.execute(request, response);

            if (view.contains("redirect:")) {
                response.sendRedirect(view.replace("redirect:", "/jsp"));
            } else {
                request.getRequestDispatcher("/WEB-INF/jsp" + view + ".jsp").forward(request, response);
            }
        } catch (final Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
	}

	
}
