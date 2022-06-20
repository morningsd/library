package edu.demian.model.dao;

import edu.demian.model.entity.Book;
import edu.demian.model.entity.BookStatus;

import java.util.List;

public interface BookDao {

    Book save(Book book, Integer quantity);

    void update(Book book);

    void delete(Long id);

    Book find(Long id);

    List<Book> findAll(String nameOrder, String authorOrder, String publisherOrder, String publishedDateOrder, int limit, long offset);

    public Book findInStock(Long uniqueBookId);

    List<Book> findAllActiveForAccount(Long id);

    List<Book> searchAll(String searchBy, String searchData, String nameOrder, String authorOrder, String publisherOrder, String publishedDateOrder, int limit, long offset);

    void setStatus(Long bookId, Integer statusId);
}
