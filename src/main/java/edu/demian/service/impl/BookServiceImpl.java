package edu.demian.service.impl;

import edu.demian.model.dao.BookDao;
import edu.demian.model.dao.factory.DaoFactory;
import edu.demian.model.dao.factory.DaoFactoryType;
import edu.demian.model.dao.impl.BookDaoImpl;
import edu.demian.model.entity.Book;
import edu.demian.service.BookService;

import java.util.List;

public final class BookServiceImpl implements BookService {

    private final BookDao bookDao = DaoFactory.getBookDao(DaoFactoryType.POSTGRESQL);

    @Override
    public Book save(final Book book, final Integer quantity) {
        return bookDao.save(book, quantity);
    }

    @Override
    public List<Book> findAllForUser(final Long id) {
        return bookDao.findAllActiveForAccount(id);
    }

    @Override
    public List<Book> searchAll(final String searchBy, final String searchData, final String nameOrder, final String authorOrder,
                                final String publisherOrder, final String publisherDateOrder, final int limit, final long offset) {
        return bookDao.searchAll(searchBy, searchData, nameOrder, authorOrder, publisherOrder, publisherDateOrder, limit, offset);
    }

    @Override
    public List<Book> findAll(final String nameOrder, final String authorOrder, final String publisherOrder, final String publisherDateOrder,
                              final int limit, final long offset) {
        return bookDao.findAll(nameOrder, authorOrder, publisherOrder, publisherDateOrder, limit, offset);
    }

    @Override
    public void setStatus(Long bookId, Integer statusId) {
        bookDao.setStatus(bookId, statusId);
    }

}
