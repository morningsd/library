package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.entity.Book;
import edu.demian.service.BookService;
import edu.demian.service.impl.BookServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public final class CatalogPageAction extends Action {

    private static final String APPLICATION_PROPERTIES = "/application.properties";

    private final BookService bookService = new BookServiceImpl();


    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        final HttpSession session = request.getSession();

        final String requestNameOrder = request.getParameter("sort_name");
        final String requestAuthorOrder = request.getParameter("sort_author");
        final String requestPublisherOrder = request.getParameter("sort_publisher");
        final String requestPublishedDateOrder = request.getParameter("sort_date");

        final String searchData = request.getParameter("search_data");
        final String searchBy = request.getParameter("search_by");

        final String nameOrder = (requestNameOrder == null) ? (String) getAttribute(session, "nameOrder", "catalog.sort.") : requestNameOrder;
        final String authorOrder = (requestAuthorOrder == null) ? (String) getAttribute(session, "authorOrder", "catalog.sort.") : requestAuthorOrder;
        final String publisherOrder = (requestPublisherOrder == null) ? (String) getAttribute(session, "publisherOrder", "catalog.sort.") : requestPublisherOrder;
        final String publisherDateOrder = (requestPublishedDateOrder == null) ? (String) getAttribute(session, "publishedDateOrder", "catalog.sort.") : requestPublishedDateOrder;
        final int limit = Integer.parseInt(getApplicationProperty("catalog.sort.limit"));

        final String currentPage = (String) getAttribute(session, "currentPage", "catalog.sort.");

        final long offset = getOffset(Long.parseLong(currentPage), limit);

        session.setAttribute("nameOrder", nameOrder);
        session.setAttribute("authorOrder", authorOrder);
        session.setAttribute("publisherOrder", publisherOrder);
        session.setAttribute("publishedDateOrder", publisherDateOrder);
        session.setAttribute("currentPage", currentPage);

        session.setAttribute("searchData", searchData);
        session.setAttribute("searchBy", searchBy);

        List<Book> bookList;
        if (searchData != null) {
            bookList = bookService.searchAll(searchBy, searchData, nameOrder, authorOrder, publisherOrder, publisherDateOrder, limit, offset);
        } else {
            bookList = bookService.findAll(nameOrder, authorOrder, publisherOrder, publisherDateOrder, limit, offset);
        }

        session.setAttribute("bookList", bookList);
        return "/catalog";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        throw new UnsupportedOperationException("This url does not support POST method");
    }

    // TODO refactor and move it to book service (mb as a static method)
    private Object getAttribute(final HttpSession session, final String name, final String propertyPrefix) {
        final Object userValue = session.getAttribute(name);
        final Object propertiesValue = getApplicationProperty(propertyPrefix + name);
        return userValue == null ? propertiesValue : userValue;
    }

    private String getApplicationProperty(final String name) {
        try {
            final Properties properties = new Properties();
            properties.load(CatalogPageAction.class.getResourceAsStream(APPLICATION_PROPERTIES));
            return properties.getProperty(name);
        } catch (IOException e) {
            throw new ActionException("Can't read property [" + name + "] from file: " + APPLICATION_PROPERTIES , e);
        }
    }

    private long getOffset(final long currentPage, final int limit) {
        // curP = 1 ==> limit numOfRecords, offset 0 (curP - 1) * numOfRecords
        // curP = 2 ==> limit numOfRecords, offset 5 (curP - 1) * numOfRecords
        return (currentPage - 1) * limit;
    }
}
