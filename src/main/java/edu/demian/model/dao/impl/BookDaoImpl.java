package edu.demian.model.dao.impl;

import edu.demian.model.DBManager;
import edu.demian.model.dao.BookDao;
import edu.demian.model.entity.Book;
import edu.demian.model.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private static final DBManager DB_MANAGER_INSTANCE = DBManager.getInstance();

    private static final String SQL_BOOK_ID = "id";
    private static final String SQL_BOOK_NAME = "name";
    private static final String SQL_BOOK_AUTHOR = "author";
    private static final String SQL_BOOK_PUBLISHER = "publisher";
    private static final String SQL_BOOK_PUBLISHED_DATE = "published_date";
    private static final String SQL_BOOK_QUANTITY = "quantity";
    private static final String SQL_BOOK_ACCOUNT_ID = "account_id";

    private static final String SQL_FIND_BOOK = "SELECT * FROM book WHERE id = ?";
    private static final String SQL_SAVE_BOOK = "INSERT INTO book " +
            "(name, author, publisher, published_date, quantity, account_id) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE_BOOK = "UPDATE book SET name=?, author=?, publisher=?, published_date=?, quantity=?, account_id=? WHERE id=?";
    private static final String SQL_DELETE_BOOK = "DELETE FROM book WHERE id=?";
    private static final String SQL_FIND_ALL_BOOKS = "SELECT * FROM book ORDER BY name %s, author %s, publisher %s, published_date %s LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_BOOKS_FOR_ACCOUNT = "SELECT * FROM book WHERE id IN (SELECT id FROM reserve WHERE account_id=? AND is_active)";
    private static final String SQL_SEARCH_BOOK = "SELECT * FROM book WHERE %s ILIKE ? ORDER BY name %s, author %s, publisher %s, published_date %s LIMIT ? OFFSET ?";

    public Book find(Long id) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_BOOK)) {
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = pstmt.getMetaData();
            return toBook(metaData, rs);
        } catch (SQLException e) {
            throw new DaoException("Can't find a book", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }


    public List<Book> findAll(String nameOrder, String authorOrder, String publisherOrder, String publishedDateOrder, int limit, long offset) {
        String formattedQuery = String.format(SQL_FIND_ALL_BOOKS, nameOrder, authorOrder, publisherOrder, publishedDateOrder);
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(formattedQuery)) {
            pstmt.setInt(1, limit);
            pstmt.setLong(2, offset);
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            return toBookList(metaData, rs);
        } catch (SQLException e) {
            throw new DaoException("Can't find all books", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    public List<Book> findAllActiveForAccount(Long id) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ALL_BOOKS_FOR_ACCOUNT)) {
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            return toBookList(metaData, rs);
        } catch (SQLException e) {
            throw new DaoException("Can't find all books for account", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    public List<Book> searchAll(String searchBy, String searchData,  String nameOrder, String authorOrder, String publisherOrder, String publishedDateOrder, int limit, long offset) {
        String formattedQuery = String.format(SQL_SEARCH_BOOK, searchBy, nameOrder, authorOrder, publisherOrder, publishedDateOrder);
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(formattedQuery)) {
            pstmt.setString(1, "%" + searchData + "%");
            pstmt.setInt(2, limit);
            pstmt.setLong(3, offset);
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            return toBookList(metaData, rs);
        } catch (SQLException e) {
            throw new DaoException("Can't search for books", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }


    public void save(Book book) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SQL_SAVE_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;
            pstmt.setString(k++, book.getName());
            pstmt.setString(k++, book.getAuthor());
            pstmt.setString(k++, book.getPublisher());
            pstmt.setObject(k++, book.getPublishedDate());
            pstmt.setInt(k++, book.getQuantity());
            Long bookOwner = book.getAccountId();
            if (bookOwner == null) {
                pstmt.setNull(k, Types.BIGINT);
            } else {
                pstmt.setLong(k, bookOwner);
            }
            if (pstmt.executeUpdate() != 1) {
                    throw new DaoException("Can't save a book");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't save a book", e);
        }
    }

    public void update(Book book) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_BOOK)) {
            int k = 1;
            pstmt.setString(k++, book.getName());
            pstmt.setString(k++, book.getAuthor());
            pstmt.setString(k++, book.getPublisher());
            pstmt.setObject(k++, book.getPublishedDate());
            pstmt.setInt(k++, book.getQuantity());
            pstmt.setLong(k++, book.getAccountId());
            pstmt.setLong(k, book.getId());
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't update a book");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update a book", e);
        }
    }


    public void delete(Long id) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_BOOK)) {
                pstmt.setLong(1, id);
                if (pstmt.executeUpdate() != 1) {
                    throw new DaoException("Can't delete a book");
                }
        } catch (SQLException e) {
            throw new DaoException("Can't delete a book", e);
        }
    }

    private List<Book> toBookList(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        List<Book> bookList = new LinkedList<>();

        while (true) {
            Book book = toBook(metaData, resultSet);
            if (book == null) {
                break;
            }
            bookList.add(book);
        }

        return bookList;
    }

    private Book toBook(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Book book = new Book();

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
                case SQL_BOOK_QUANTITY:
                    book.setQuantity(resultSet.getInt(i));
                    break;
                case SQL_BOOK_ACCOUNT_ID:
                    book.setAccountId(resultSet.getLong(i));
                    break;
                default:
                    // No operations
            }
        }
        return book;
    }
}
