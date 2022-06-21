package edu.demian.controller.action.impl;

import edu.demian.annotation.PageAccessor;
import edu.demian.annotation.PageAccessorType;
import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@PageAccessor(allowedTo = {PageAccessorType.LIBRARIAN})
public class ReadersPageAction extends Action {
    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return "/librarian/readers";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}
