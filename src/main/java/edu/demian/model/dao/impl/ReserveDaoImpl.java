package edu.demian.model.dao.impl;

import edu.demian.model.DBManager;
import edu.demian.model.dao.ReserveDao;
import edu.demian.model.entity.Reserve;
import edu.demian.model.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;

public class ReserveDaoImpl implements ReserveDao {

    private static final DBManager DB_MANAGER_INSTANCE = DBManager.getInstance();

    private static final String SQL_RESERVE_ID = "id";
    private static final String SQL_RESERVE_ACCOUNT_ID = "account_id";
    private static final String SQL_RESERVE_BOOK_ID = "book_id";
    private static final String SQL_RESERVE_CREATED_DATE = "created_date";
    private static final String SQL_RESERVE_IS_ACTIVE = "is_active";

    private static final String SQL_INSERT_RESERVE = "INSERT INTO reserve(account_id, book_id) VALUES (?,?)";


    public void save(Reserve reserve) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_INSERT_RESERVE, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, reserve.getAccountId());
            pstmt.setLong(2, reserve.getBookId());
            if (pstmt.executeUpdate() == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    long id = rs.getLong(1);
                    reserve.setId(id);
                } else {
                    throw new DaoException("Can't save an order");
                }
            } else {
                throw new DaoException("Can't save an order");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't save an order", e);
        }
    }


    private Reserve toReserve(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Reserve reserve = new Reserve();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case SQL_RESERVE_ID:
                    reserve.setId(resultSet.getLong(i));
                    break;
                case SQL_RESERVE_ACCOUNT_ID:
                    reserve.setAccountId(resultSet.getLong(i));
                    break;
                case SQL_RESERVE_BOOK_ID:
                    reserve.setBookId(resultSet.getLong(i));
                    break;
                case SQL_RESERVE_CREATED_DATE:
                    reserve.setCreatedDate(resultSet.getObject(i, LocalDate.class));
                case SQL_RESERVE_IS_ACTIVE:
                    reserve.setActive(resultSet.getBoolean(i));
                default:
                    // No operations
            }
        }
        return reserve;
    }


}
