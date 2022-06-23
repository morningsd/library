package edu.demian.model.dao;

import edu.demian.model.entity.Book;
import edu.demian.model.entity.BookStatus;

import java.util.List;

public interface BookDao {

    Book save(Book book, int quantity);

    void delete(long id);

    Book find(long id);

    List<Book> findAll(String searchBy, String searchData, String sortOrder, int limit, long offset);

    List<Book> findAll(String searchBy, String sortOrder, int limit, long offset);

    List<Book> findAll(String nameOrder, String authorOrder, String publisherOrder, String publishedDateOrder, int limit, long offset);

    Book findInStock(long uniqueBookId);

    List<Book> findAllActiveForAccount(long id);

    void setStatus(long bookId, int statusId);
}
