package edu.demian.service.impl;

import edu.demian.model.dao.BookDao;
import edu.demian.model.dao.impl.BookDaoImpl;
import edu.demian.model.entity.Book;
import edu.demian.service.BookService;

import java.util.List;

public final class BookServiceImpl implements BookService {

    private final BookDao bookDAO = new BookDaoImpl();

    @Override
    public void save(final Book book) {
        bookDAO.save(book);
    }

    @Override
    public List<Book> findAllForUser(final Long id) {
        return bookDAO.findAllActiveForAccount(id);
    }

    @Override
    public List<Book> searchAll(final String searchBy, final String searchData, final String nameOrder, final String authorOrder,
                                final String publisherOrder, final String publisherDateOrder, final int limit, final long offset) {
        return bookDAO.searchAll(searchBy, searchData, nameOrder, authorOrder, publisherOrder, publisherDateOrder, limit, offset);
    }

    @Override
    public List<Book> findAll(final String nameOrder, final String authorOrder, final String publisherOrder, final String publisherDateOrder,
                              final int limit, final long offset) {
        return bookDAO.findAll(nameOrder, authorOrder, publisherOrder, publisherDateOrder, limit, offset);
    }

}
