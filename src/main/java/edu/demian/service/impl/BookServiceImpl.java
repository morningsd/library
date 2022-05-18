package edu.demian.service.impl;

import edu.demian.model.dao.BookDao;
import edu.demian.model.dao.impl.BookDaoImpl;
import edu.demian.model.entity.Book;
import edu.demian.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookServiceImpl implements BookService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private final BookDao bookDAO = new BookDaoImpl();

    public void saveBook(HttpServletRequest request) {
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
    }


}
