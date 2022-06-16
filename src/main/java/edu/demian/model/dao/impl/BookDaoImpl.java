package edu.demian.model.dao.impl;

import edu.demian.model.DBManager;
import edu.demian.model.dao.BookDao;
import edu.demian.model.entity.Book;
import edu.demian.model.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public final class BookDaoImpl implements BookDao {

    private static final DBManager DB_MANAGER_INSTANCE = DBManager.getInstance();

    private static final String SQL_BOOK_ID = "id";
    private static final String SQL_BOOK_NAME = "name";
    private static final String SQL_BOOK_AUTHOR = "author";
    private static final String SQL_BOOK_PUBLISHER = "publisher";
    private static final String SQL_BOOK_PUBLISHED_DATE = "published_date";
    private static final String SQL_BOOK_STATUS_ID = "status_id";
//    private static final String SQL_BOOK_ACCOUNT_ID = "account_id";


    private static final String SQL_FIND_BOOK = "SELECT * FROM book WHERE id = ?";
    private static final String SQL_SAVE_BOOK = "INSERT INTO book " +
            "(name, author, publisher, published_date, status_id) VALUES (?,?,?,?,?)";
    private static final String SQL_SAVE_BOOK_TO_CATALOG = "INSERT INTO catalog (book_id, quantity) VALUES (?,?)";
    private static final String SQL_UPDATE_BOOK = "UPDATE book SET name=?, author=?, publisher=?, published_date=?, status_id=? WHERE id=?";
    private static final String SQL_DELETE_BOOK = "DELETE FROM book WHERE id=?";
    private static final String SQL_FIND_ALL_BOOKS = "SELECT * FROM book ORDER BY name %s, author %s, publisher %s, published_date %s LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_BOOKS_FOR_ACCOUNT = "SELECT * FROM book WHERE id IN (SELECT book_id FROM reserve WHERE account_id=? AND is_active)";
    private static final String SQL_SEARCH_BOOK = "SELECT * FROM book WHERE %s ILIKE ? ORDER BY name %s, author %s, publisher %s, published_date %s LIMIT ? OFFSET ?";

    public Book find(final Long id) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_BOOK)) {
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = pstmt.getMetaData();
            return toBook(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find a book", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }


    public List<Book> findAll(final String nameOrder, final String authorOrder, final String publisherOrder, final String publishedDateOrder, final int limit, final long offset) {
        final String formattedQuery = String.format(SQL_FIND_ALL_BOOKS, nameOrder, authorOrder, publisherOrder, publishedDateOrder);
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(formattedQuery)) {
            pstmt.setInt(1, limit);
            pstmt.setLong(2, offset);
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            return toBookList(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find all books", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    public List<Book> findAllActiveForAccount(final Long id) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ALL_BOOKS_FOR_ACCOUNT)) {
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            return toBookList(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find all books for account", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    public List<Book> searchAll(final String searchBy, final String searchData, final String nameOrder, final String authorOrder,
                                final String publisherOrder, final String publishedDateOrder, final int limit, final long offset) {
        final String formattedQuery = String.format(SQL_SEARCH_BOOK, searchBy, nameOrder, authorOrder, publisherOrder, publishedDateOrder);
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(formattedQuery)) {
            pstmt.setString(1, "%" + searchData + "%");
            pstmt.setInt(2, limit);
            pstmt.setLong(3, offset);
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            return toBookList(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't search for books", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }


    public Book save(final Book book, final Integer quantity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        try {
            connection = DB_MANAGER_INSTANCE.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQL_SAVE_BOOK, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, book.getName());
            pstmt.setString(k++, book.getAuthor());
            pstmt.setString(k++, book.getPublisher());
            pstmt.setObject(k++, book.getPublishedDate());
            pstmt.setInt(k, book.getStatusId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                book.setId(rs.getLong(SQL_BOOK_ID));
            } else {
                throw new DaoException("Can't save a book");
            }
            pstmt2 = connection.prepareStatement(SQL_SAVE_BOOK_TO_CATALOG);
            pstmt2.setLong(1, book.getId());
            pstmt2.setInt(2, quantity);
            if (pstmt2.executeUpdate() != 1) {
                throw new DaoException("Can't save a book to the catalog");
            }
            DB_MANAGER_INSTANCE.commit(connection);
        } catch (final SQLException e) {
            DB_MANAGER_INSTANCE.rollback(connection);
            throw new DaoException("Can't save a book", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
            DB_MANAGER_INSTANCE.close(pstmt2);
            DB_MANAGER_INSTANCE.close(pstmt);
            DB_MANAGER_INSTANCE.close(connection);
        }
        return book;
    }

    public void update(final Book book) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_BOOK)) {
            int k = 1;
            pstmt.setString(k++, book.getName());
            pstmt.setString(k++, book.getAuthor());
            pstmt.setString(k++, book.getPublisher());
            pstmt.setObject(k++, book.getPublishedDate());
            pstmt.setInt(k++, book.getStatusId());
//            pstmt.setInt(k++, book.getQuantity());
//            pstmt.setLong(k++, book.getAccountId());
            pstmt.setLong(k, book.getId());
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't update a book");
            }
        } catch (final SQLException e) {
            throw new DaoException("Can't update a book", e);
        }
    }


    public void delete(final Long id) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_BOOK)) {
            pstmt.setLong(1, id);
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't delete a book");
            }
        } catch (final SQLException e) {
            throw new DaoException("Can't delete a book", e);
        }
    }

    private List<Book> toBookList(final ResultSetMetaData metaData, final ResultSet resultSet) throws SQLException {
        final List<Book> bookList = new LinkedList<>();

        while (true) {
            final Book book = toBook(metaData, resultSet);
            if (book == null) {
                break;
            }
            bookList.add(book);
        }

        return bookList;
    }

    private Book toBook(final ResultSetMetaData metaData, final ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        final Book book = new Book();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case SQL_BOOK_ID:
                    book.setId(resultSet.getLong(i));
                    break;
                case SQL_BOOK_NAME:
                    book.setName(resultSet.getString(i));
                    break;
                case SQL_BOOK_AUTHOR:
                    book.setAuthor(resultSet.getString(i));
                    break;
                case SQL_BOOK_PUBLISHER:
                    book.setPublisher(resultSet.getString(i));
                    break;
                case SQL_BOOK_PUBLISHED_DATE:
                    book.setPublishedDate(resultSet.getObject(i, LocalDate.class));
                    break;
                case SQL_BOOK_STATUS_ID:
                    book.setStatusId(resultSet.getInt(i));
                    break;
//                case SQL_BOOK_QUANTITY:
//                    book.setQuantity(resultSet.getInt(i));
//                    break;
//                case SQL_BOOK_ACCOUNT_ID:
//                    book.setAccountId(resultSet.getLong(i));
//                    break;
                default:
                    // No operations
            }
        }
        return book;
    }
}
