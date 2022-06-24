package edu.demian.web.controller.page.jsp;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;

import javax.servlet.http.HttpServletRequest;

@PageAccessor(allowedTo = {PageAccessorType.NOT_LOGGED, PageAccessorType.READER, PageAccessorType.LIBRARIAN, PageAccessorType.ADMINISTRATOR})
public class ErrorPage {

    private void action(final HttpServletRequest request) {

    }

}
