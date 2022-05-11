package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CabinetPageAction extends Action {
    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        return "/cabinet";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}
