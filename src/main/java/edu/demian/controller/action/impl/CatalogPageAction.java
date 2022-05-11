package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.BookDAO;
import edu.demian.model.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class CatalogPageAction extends Action {

    private static final String APPLICATION_PROPERTIES = "/application.properties";

    private final BookDAO bookDAO = new BookDAO();


    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String searchData = request.getParameter("search_data");
        String searchBy = request.getParameter("search_by");

        String nameOrder = (String) getAttribute(session, "nameOrder", "catalog.sort.");
        String authorOrder = (String) getAttribute(session, "authorOrder", "catalog.sort.");
        String publisherOrder = (String) getAttribute(session, "publisherOrder", "catalog.sort.");
        String publisherDateOrder = (String) getAttribute(session, "publishedDateOrder", "catalog.sort.");
        int limit = Integer.parseInt(getApplicationProperty("catalog.sort.limit"));

        String currentPage = (String) getAttribute(session, "currentPage", "catalog.sort.");

        long offset = getOffset(Long.parseLong(currentPage), limit);

        session.setAttribute("nameOrder", nameOrder);
        session.setAttribute("authorOrder", authorOrder);
        session.setAttribute("publisherOrder", publisherOrder);
        session.setAttribute("publishedDateOrder", publisherDateOrder);
        session.setAttribute("currentPage", currentPage);

        session.setAttribute("searchData", searchData);
        session.setAttribute("searchBy", searchBy);

        List<Book> bookList;
        if (searchData != null) {
            bookList = bookDAO.searchAll(searchBy, searchData, nameOrder, authorOrder, publisherOrder, publisherDateOrder, limit, offset);
        } else {
            bookList = bookDAO.findAll(nameOrder, authorOrder, publisherOrder, publisherDateOrder, limit, offset);
        }

        session.setAttribute("bookList", bookList);
        return "/catalog";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("This url does not support POST method");
    }

    private Object getAttribute(HttpSession session, String name, String propertyPrefix) {
        Object userValue = session.getAttribute(name);
        Object propertiesValue = getApplicationProperty(propertyPrefix + name);
        return userValue == null ? propertiesValue : userValue;
    }

    private String getApplicationProperty(String name) {
        try {
            Properties properties = new Properties();
            properties.load(CatalogPageAction.class.getResourceAsStream(APPLICATION_PROPERTIES));
            return properties.getProperty(name);
        } catch (IOException e) {
            throw new ActionException("Can't read property [" + name + "] from file: " + APPLICATION_PROPERTIES , e);
        }
    }

    private long getOffset(long currentPage, int limit) {
        // curP = 1 ==> limit numOfRecords, offset 0 (curP - 1) * numOfRecords
        // curP = 2 ==> limit numOfRecords, offset 5 (curP - 1) * numOfRecords
        return (currentPage - 1) * limit;
    }
}
