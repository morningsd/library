package edu.demian.service;

import edu.demian.model.entity.Book;

import java.util.List;

public interface BookService {

    Book save(Book book, int quantity);

    List<Book> findAllForUser(long id);

    List<Book> findAll(String searchBy, String searchData, String sortOrder, int limit, long offset);

    List<Book> findAll(String searchBy, String sortOrder, int limit, long offset);

    List<Book> findAll(String nameOrder, String authorOrder, String publisherOrder, String publisherDateOrder, int limit, long offset);

    void makeSubscription(long bookId, int statusId);
}
