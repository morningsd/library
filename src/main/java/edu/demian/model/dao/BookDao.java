package edu.demian.model.dao;

import edu.demian.model.entity.Book;
import edu.demian.model.entity.BookStatus;

import java.util.List;

public interface BookDao {

    Book find(Long id);

    public Book findInStock(Long uniqueBookId);

    List<Book> findAll(String nameOrder, String authorOrder, String publisherOrder, String publishedDateOrder, int limit, long offset);

    List<Book> findAllActiveForAccount(Long id);

    List<Book> searchAll(String searchBy, String searchData, String nameOrder, String authorOrder, String publisherOrder, String publishedDateOrder, int limit, long offset);

    Book save(Book book, Integer quantity);

    void update(Book book);

    void delete(Long id);


    void setStatus(Long bookId, Integer statusId);
}
