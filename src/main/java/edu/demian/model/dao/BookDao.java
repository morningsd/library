package edu.demian.model.dao;

import edu.demian.model.entity.Book;

import java.util.List;

public interface BookDao {

    Book find(Long id);

    List<Book> findAll(String nameOrder, String authorOrder, String publisherOrder, String publishedDateOrder, int limit, long offset);

    List<Book> findAllActiveForAccount(Long id);

    List<Book> searchAll(String searchBy, String searchData,  String nameOrder, String authorOrder, String publisherOrder, String publishedDateOrder, int limit, long offset);

    void save(Book book);

    void update(Book book);

    void delete(Long id);



}
