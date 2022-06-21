package edu.demian.controller.action.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.demian.annotation.PageAccessor;
import edu.demian.annotation.PageAccessorType;
import edu.demian.controller.action.Action;

@PageAccessor(allowedTo = {PageAccessorType.ALL})
public final class HomePageAction extends Action {

	@Override
	protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
		return "/home";
	}

	@Override
	protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
		throw new UnsupportedOperationException("This url does not support POST method");
	}


}
