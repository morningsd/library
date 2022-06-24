package edu.demian.model.dao.impl;

import edu.demian.model.DBManager;
import edu.demian.model.dao.BookDao;
import edu.demian.model.entity.Book;
import edu.demian.model.entity.BookStatus;
import edu.demian.model.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private static final DBManager DB_MANAGER_INSTANCE = DBManager.getInstance();

    private static final String SQL_UNIQUE_BOOK_ID = "id";
    private static final String SQL_UNIQUE_BOOK_NAME = "name";
    private static final String SQL_UNIQUE_BOOK_AUTHOR = "author";
    private static final String SQL_UNIQUE_BOOK_PUBLISHER = "publisher";
    private static final String SQL_UNIQUE_BOOK_PUBLISHED_DATE = "published_date";
    private static final String SQL_BOOK_QUANTITY = "quantity";

    private static final String SQL_BOOK_ID = "id";
    private static final String SQL_BOOK_UNIQUE_ID = "book_id";
    private static final String SQL_BOOK_STATUS_ID = "status_id";


    private static final String SQL_FIND_BOOK = "SELECT b1.id, b2.name, b2.author, b2.publisher, b2.published_date, b1.status_id FROM book b1 JOIN unique_book b2 ON b1.book_id = b2.id WHERE b1.id=?";

    private static final String SQL_FIND_BOOK_IN_STOCK = "SELECT b1.id, b2.name, b2.author, b2.publisher, b2.published_date, b1.status_id FROM book b1 JOIN unique_book b2 ON b1.book_id = b2.id WHERE b1.id=((SELECT id FROM book WHERE book_id=? AND status_id=1 LIMIT 1))";
    private static final String SQL_SAVE_BOOK = "INSERT INTO book (book_id, status_id) VALUES (?, ?)";
    private static final String SQL_SAVE_UNIQUE_BOOK = "INSERT INTO unique_book " +
            "(name, author, publisher, published_date) VALUES (?,?,?,?)";
    private static final String SQL_DELETE_BOOK = "DELETE FROM book WHERE id=?";
    private static final String SQL_FIND_ALL_BOOKS = "SELECT b.*, (SELECT COUNT(id) FROM book WHERE book_id=b.id AND status_id=1) as quantity FROM unique_book b ORDER BY name %s, author %s, publisher %s, published_date %s LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_BOOKS_FOR_ACCOUNT = "SELECT b1.id, b2.name, b2.author, b2.publisher, b2.published_date FROM book b1 JOIN unique_book b2 ON b1.book_id = b2.id WHERE b1.id IN (SELECT book_id FROM reserve WHERE account_id=? AND is_active)";
    private static final String SQL_FIND_ALL_BOOKS_IN_STOCK_LIKE = "SELECT b.*, (SELECT COUNT(id) FROM book WHERE book_id=b.id AND status_id=1) as quantity FROM unique_book b WHERE %s ILIKE ? ORDER BY %s %s LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_BOOKS_IN_STOCK = "SELECT b.*, (SELECT COUNT(id) FROM book WHERE book_id=b.id AND status_id=1) as quantity FROM unique_book b ORDER BY %s %s LIMIT ? OFFSET ?";
    private static final String SQL_UPDATE_STATUS = "UPDATE book SET status_id=? WHERE id=?";

    public Book find(final long id) {
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

    @Override
    public List<Book> findAll(String searchBy, String searchData, String sortOrder, int limit, long offset) {
        String formattedQuery;
        if ("published_date".equalsIgnoreCase(searchBy)) {
            formattedQuery = String.format(SQL_FIND_ALL_BOOKS_IN_STOCK_LIKE, "CAST(" + searchBy + " AS VARCHAR)", searchBy, sortOrder);
        } else {
            //SELECT b.*, (SELECT COUNT(id) FROM book WHERE book_id=b.id AND status_id=1) as quantity FROM unique_book b WHERE %s ILIKE ? ORDER BY %s %s LIMIT ? OFFSET ?
            formattedQuery = String.format(SQL_FIND_ALL_BOOKS_IN_STOCK_LIKE, searchBy, searchBy, sortOrder);
        }
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
            throw new DaoException("Can't find all books", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    @Override
    public List<Book> findAll(String searchBy, String sortOrder, int limit, long offset) {
        //SELECT b.*, (SELECT COUNT(id) FROM book WHERE book_id=b.id AND status_id=1) as quantity FROM unique_book b ORDER BY %s %s LIMIT ? OFFSET ?
        final String formattedQuery = String.format(SQL_FIND_ALL_BOOKS_IN_STOCK, searchBy, sortOrder);
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

    public Book findInStock(final long uniqueBookId) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_BOOK_IN_STOCK)) {
            pstmt.setLong(1, uniqueBookId);
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = pstmt.getMetaData();
            return toBook(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find a book in stock", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    public Book save(final Book book, final int quantity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        try {
            connection = DB_MANAGER_INSTANCE.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQL_SAVE_UNIQUE_BOOK, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, book.getName());
            pstmt.setString(k++, book.getAuthor());
            pstmt.setString(k++, book.getPublisher());
            pstmt.setObject(k, book.getPublishedDate());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Can't save a book");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(SQL_UNIQUE_BOOK_ID));
                }
                else {
                    throw new DaoException("Can't save a book");
                }
            }

            pstmt2 = connection.prepareStatement(SQL_SAVE_BOOK);
            for (int i = 0; i < quantity; i++) {
                pstmt2.setLong(1, book.getId());
                pstmt2.setInt(2, book.getStatusId());
                pstmt2.addBatch();
            }
            pstmt2.executeBatch();
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


    public void delete(final long id) {
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

    @Override
    public void makeSubscription(long bookId, int statusId) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_STATUS)) {
            pstmt.setInt(1, statusId);
            pstmt.setObject(2, LocalDate.now());
            pstmt.setLong(2, bookId);
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't update book status");
            }
        } catch (final SQLException e) {
            throw new DaoException("Can't update book status", e);
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

    public List<Book> findAllActiveForAccount(final long id) {
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
                case SQL_UNIQUE_BOOK_NAME:
                    book.setName(resultSet.getString(i));
                    break;
                case SQL_UNIQUE_BOOK_AUTHOR:
                    book.setAuthor(resultSet.getString(i));
                    break;
                case SQL_UNIQUE_BOOK_PUBLISHER:
                    book.setPublisher(resultSet.getString(i));
                    break;
                case SQL_UNIQUE_BOOK_PUBLISHED_DATE:
                    book.setPublishedDate(resultSet.getObject(i, LocalDate.class));
                    break;
                case SQL_BOOK_STATUS_ID:
                    final int statusId = resultSet.getInt(i);
                    book.setStatusId(statusId);
                    book.setStatus(BookStatus.getBookStatus(statusId));
                    break;
                case SQL_BOOK_QUANTITY:
                    book.setQuantity(resultSet.getInt(i));
                    break;
                default:
                    // No operations
            }
        }
        return book;
    }
}
