package edu.demian.model.dao.impl;

import edu.demian.model.DBManager;
import edu.demian.model.dao.AccountDao;
import edu.demian.model.dao.BookDao;
import edu.demian.model.dao.ReserveDao;
import edu.demian.model.entity.Book;
import edu.demian.model.entity.Reserve;
import edu.demian.model.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ReserveDaoImpl implements ReserveDao {

    private static final DBManager DB_MANAGER_INSTANCE = DBManager.getInstance();
    private final BookDao bookDao = new BookDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();

    private static final String SQL_RESERVE_ID = "id";
    private static final String SQL_RESERVE_ACCOUNT_ID = "account_id";
    private static final String SQL_RESERVE_BOOK_ID = "book_id";
    private static final String SQL_RESERVE_CREATED_DATE = "created_date";
    private static final String SQL_RESERVE_FINAL_DATE = "final_date";
    private static final String SQL_RESERVE_IS_ACTIVE = "is_active";
    private static final String SQL_RESERVE_SUBMITTED_DATE = "submitted_date";
    private static final String SQL_RESERVE_START_DATE = "start_date";

    private static final String SQL_INSERT_RESERVE = "INSERT INTO reserve(account_id, book_id) VALUES (?,?)";
    private static final String SQL_RESERVE_BOOK = "UPDATE book SET status_id=2 WHERE id=?";
    private static final String SQL_FIND_ALL_ACTIVE_RESERVES_FOR_ACCOUNT = "SELECT * FROM reserve WHERE account_id=? AND is_active";
    private static final String SQL_FIND_ALL_RESERVES_FOR_ACCOUNT = "SELECT * FROM reserve WHERE account_id=?";
    private static final String SQL_FIND_ALL_ACTIVE = "SELECT * FROM reserve WHERE is_active AND book_id IN (SELECT id FROM book WHERE status_id=2) ORDER BY created_date ASC";
    private static final String SQL_CHANGE_STATUS = "UPDATE reserve SET is_active=? WHERE id=?";
    private static final String SQL_CHANGE_START_DATE = "UPDATE reserve SET start_date=? WHERE id=?";
    private static final String SQL_CHANGE_FINAL_DATE = "UPDATE reserve SET final_date=? WHERE id=?";
    private static final String SQL_CHANGE_SUBMITTED_DATE = "UPDATE reserve SET submitted_date=? WHERE id=?";


    public void save(final Reserve reserve) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        try {
            Book book = bookDao.findInStock(reserve.getBookId());
            reserve.setBookId(book.getId());

            connection = DB_MANAGER_INSTANCE.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQL_INSERT_RESERVE, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, reserve.getAccountId());
            pstmt.setLong(2, reserve.getBookId());
            if (pstmt.executeUpdate() == 1) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    final long id = rs.getLong(1);
                    reserve.setId(id);
                } else {
                    throw new DaoException("Can't save an order");
                }
            } else {
                throw new DaoException("Can't save an order");
            }
            pstmt2 = connection.prepareStatement(SQL_RESERVE_BOOK);
            pstmt2.setLong(1, reserve.getBookId());
            if (pstmt2.executeUpdate() != 1) {
                throw new DaoException("Can't save an order");
            }
            DB_MANAGER_INSTANCE.commit(connection);
        } catch (final SQLException e) {
            DB_MANAGER_INSTANCE.rollback(connection);
            throw new DaoException("Can't save an order", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
            DB_MANAGER_INSTANCE.close(pstmt2);
            DB_MANAGER_INSTANCE.close(pstmt);
            DB_MANAGER_INSTANCE.close(connection);
        }
    }

    @Override
    public List<Reserve> findAllActiveForUser(long id) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ALL_ACTIVE_RESERVES_FOR_ACCOUNT)) {
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            return toReserveList(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find all active reserves for account", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }


    @Override
    public List<Reserve> findAllForUser(long id) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ALL_RESERVES_FOR_ACCOUNT)) {
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            return toReserveList(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find all reserves for account", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    @Override
    public List<Reserve> findAllActive() {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ALL_ACTIVE)) {
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            return toReserveList(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find all active reserves", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    @Override
    public void setActive(long reserveId, boolean isActive) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_CHANGE_STATUS)) {
            pstmt.setBoolean(1, isActive);
            pstmt.setLong(2, reserveId);
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't set reserve isActive");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't set reserve isActive");
        }
    }

    @Override
    public void setStartDate(long reserveId, LocalDate now) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_CHANGE_START_DATE)) {
            pstmt.setObject(1, now);
            pstmt.setLong(2, reserveId);
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't set reserve startDate");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't set reserve startDate");
        }
    }

    @Override
    public void setFinalDate(long reserveId, LocalDate finalDate) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_CHANGE_FINAL_DATE)) {
            pstmt.setObject(1, finalDate);
            pstmt.setLong(2, reserveId);
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't set reserve finalDate");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't set reserve finalDate");
        }
    }

    @Override
    public void setSubmittedDate(long reserveId, LocalDate submittedDate) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_CHANGE_SUBMITTED_DATE)) {
            pstmt.setObject(1, submittedDate);
            pstmt.setLong(2, reserveId);
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't set reserve submittedDate");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't set reserve submittedDate");
        }
    }


    private List<Reserve> toReserveList(final ResultSetMetaData metaData, final ResultSet resultSet) throws SQLException {
        final List<Reserve> reserveList = new LinkedList<>();

        while (true) {
            final Reserve reserve = toReserve(metaData, resultSet);
            if (reserve == null) {
                break;
            }
            reserveList.add(reserve);
        }

        return reserveList;
    }


    private Reserve toReserve(final ResultSetMetaData metaData, final ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        final Reserve reserve = new Reserve();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case SQL_RESERVE_ID:
                    reserve.setId(resultSet.getLong(i));
                    break;
                case SQL_RESERVE_ACCOUNT_ID:
                    long accountId = resultSet.getLong(i);
                    reserve.setAccountId(accountId);
                    reserve.setAccount(accountDao.find(accountId));
                    break;
                case SQL_RESERVE_BOOK_ID:
                    long bookId = resultSet.getLong(i);
                    reserve.setBookId(bookId);
                    reserve.setBook(bookDao.find(bookId));
                    break;
                case SQL_RESERVE_START_DATE:
                    reserve.setStartDate(resultSet.getObject(i, LocalDate.class));
                    break;
                case SQL_RESERVE_CREATED_DATE:
                    reserve.setCreatedDate(resultSet.getObject(i, LocalDate.class));
                    break;
                case SQL_RESERVE_FINAL_DATE:
                    reserve.setFinalDate(resultSet.getObject(i, LocalDate.class));
                    break;
                case SQL_RESERVE_SUBMITTED_DATE:
                    reserve.setSubmittedDate(resultSet.getObject(i, LocalDate.class));
                    break;
                case SQL_RESERVE_IS_ACTIVE:
                    reserve.setActive(resultSet.getBoolean(i));
                    break;
                default:
                    // No operations
            }
        }
        return reserve;
    }


}
