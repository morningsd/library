package edu.demian.service.impl;

import edu.demian.model.dao.BookDao;
import edu.demian.model.dao.ReserveDao;
import edu.demian.model.dao.factory.DaoFactory;
import edu.demian.model.dao.factory.DaoFactoryType;
import edu.demian.model.entity.Book;
import edu.demian.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final BookDao bookDao = DaoFactory.getBookDao(DaoFactoryType.POSTGRESQL);

    @Override
    public Book save(final Book book, final int quantity) {
        return bookDao.save(book, quantity);
    }

    @Override
    public List<Book> findAllForUser(final long id) {
        return bookDao.findAllActiveForAccount(id);
    }

    @Override
    public List<Book> findAll(String searchBy, String searchData, String sortOrder, int limit, long offset) {
        return bookDao.findAll(searchBy, searchData, sortOrder, limit, offset);
    }

    @Override
    public List<Book> findAll(String searchBy, String sortOrder, int limit, long offset) {
        return bookDao.findAll(searchBy, sortOrder, limit, offset);
    }


    @Override
    public List<Book> findAll(final String nameOrder, final String authorOrder, final String publisherOrder, final String publisherDateOrder,
                              final int limit, final long offset) {
        return bookDao.findAll(nameOrder, authorOrder, publisherOrder, publisherDateOrder, limit, offset);
    }

    @Override
    public void makeSubscription(long bookId, int statusId) {
        bookDao.makeSubscription(bookId, statusId);
    }

}
