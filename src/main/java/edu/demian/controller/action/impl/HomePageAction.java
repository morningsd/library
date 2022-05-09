package edu.demian.controller.action.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.demian.controller.action.Action;

public class HomePageAction extends Action {

	@Override
	protected String doGet(HttpServletRequest request, HttpServletResponse response) {
		return "/home";
	}

	@Override
	protected String doPost(HttpServletRequest request, HttpServletResponse response) {
		throw new UnsupportedOperationException();
	}


}
