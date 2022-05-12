package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.BookDAO;
import edu.demian.model.entity.Book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddBookAction extends Action {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return "/admin/addBook";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String publishedDate = request.getParameter("published_date");
        String quantity = request.getParameter("quantity");

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPublishedDate(LocalDate.parse(publishedDate, formatter));
        book.setQuantity(Integer.parseInt(quantity));

        bookDAO.save(book);

        return "redirect:/catalog";
    }
}
