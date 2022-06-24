package edu.demian.web.controller.page.jsp.admin;

import edu.demian.model.entity.Book;
import edu.demian.model.entity.BookStatus;
import edu.demian.service.BookService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@PageAccessor(allowedTo = {PageAccessorType.ADMINISTRATOR})
public class AddBookPage {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private final BookService bookService = ServiceFactory.getBookService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {

    }

    private void addBook(final HttpServletRequest request) {
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

        bookService.save(book, Integer.parseInt(quantity));

        throw new RedirectException("/jsp/catalog");
    }
}
