package edu.demian.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionFactory;

public class FrontServlet extends HttpServlet {

	private static final long serialVersionUID = -2600384499285177295L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
            Action action = ActionFactory.getAction(request);
            String view = action.execute(request, response);

            if (view.contains("redirect:")) {
                response.sendRedirect(view.replace("redirect:", "/jsp"));
            } else {
                request.getRequestDispatcher("/WEB-INF/jsp" + view + ".jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
	}

	
}
