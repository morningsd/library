package edu.demian.controller.action.impl;

import edu.demian.annotation.PageAccessor;
import edu.demian.annotation.PageAccessorType;
import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.entity.Book;
import edu.demian.model.entity.BookStatus;
import edu.demian.service.BookService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.service.impl.BookServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@PageAccessor(allowedTo = {PageAccessorType.ADMINISTRATOR})
public final class AddBookAction extends Action {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private final BookService bookService = ServiceFactory.getBookService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        return "/admin/addBook";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        final String name = request.getParameter("name");
        final String author = request.getParameter("author");
        final String publisher = request.getParameter("publisher");
        final String publishedDate = request.getParameter("published_date");
        final String quantity = request.getParameter("quantity");

        final Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPublishedDate(LocalDate.parse(publishedDate, formatter));
        book.setStatusId(BookStatus.IN_STOCK.getId());

        Book savedBook = bookService.save(book, Integer.parseInt(quantity));

        return "redirect:/catalog";
    }
}
