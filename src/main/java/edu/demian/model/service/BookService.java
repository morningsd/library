package edu.demian.model.service;

import edu.demian.model.entity.Book;
import edu.demian.model.entity.BookStatus;

import java.util.List;

public interface BookService {

    Book save(Book book, Integer quantity);

    List<Book> findAllForUser(Long id);

    List<Book> searchAll(String searchBy, String searchData, String nameOrder, String authorOrder, String publisherOrder, String publisherDateOrder, int limit, long offset);

    List<Book> findAll(String nameOrder, String authorOrder, String publisherOrder, String publisherDateOrder, int limit, long offset);

    void setStatus(Long bookId, Integer statusId);
}