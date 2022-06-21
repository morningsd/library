package edu.demian.web.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {
	
	public String execute(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
		final String method = request.getMethod();
		if (method.equalsIgnoreCase("GET")) {
			return doGet(request, response);
		} else if (method.equalsIgnoreCase("POST")) {
			return doPost(request, response);
		}
		throw new ActionException("Unsupported method: " + method + " is not supported for path: " + request.getPathInfo());
	}

	protected abstract String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException;
	
	protected abstract String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException;
		
}
