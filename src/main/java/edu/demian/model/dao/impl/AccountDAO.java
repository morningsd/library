package edu.demian.model.dao.impl;

import com.google.common.hash.Hashing;
import edu.demian.model.DBManager;
import edu.demian.model.dao.DataAccessObject;
import edu.demian.model.entity.Account;
import edu.demian.model.exception.DaoException;

import java.nio.charset.StandardCharsets;
import java.sql.*;

public class AccountDAO implements DataAccessObject {

    private static final DBManager DB_MANAGER_INSTANCE = DBManager.getInstance();

    private static final String SQL_ACCOUNT_ID = "id";
    private static final String SQL_ACCOUNT_FNAME = "first_name";
    private static final String SQL_ACCOUNT_LNAME = "last_name";
    private static final String SQL_ACCOUNT_EMAIL = "email";
    private static final String SQL_ACCOUNT_IS_ADMIN = "is_admin";
    private static final String SQL_ACCOUNT_ROLE_ID = "role_id";


    private static final String SQL_FIND_ACCOUNT = "SELECT * FROM account WHERE id=?";
    private static final String SQL_FIND_ACCOUNT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM account WHERE email=?" +
            " AND password=?";
    private static final String SQL_INSERT_ACCOUNT = "INSERT INTO account " +
            "(first_name, last_name, email, password, is_admin, role_id) VALUES (?,?,?,?,?,?)";

    public Account find(Long id) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = pstmt.getMetaData();
            return toAccount(metaData, rs);
        } catch (SQLException e) {
            throw new DaoException("Can't find an account", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }


    public Account findByEmailAndPassword(String email, String password) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_EMAIL_AND_PASSWORD)) {
            pstmt.setString(1, email);
            pstmt.setString(2, hashPassword(password));
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = pstmt.getMetaData();
            return toAccount(metaData, rs);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }



    public void save(Account account, String password) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SQL_INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;
            pstmt.setString(k++, account.getFirstName());
            pstmt.setString(k++, account.getLastName());
            pstmt.setString(k++, account.getEmail());
            pstmt.setString(k++, hashPassword(password));
            pstmt.setBoolean(k++, account.getAdmin());
            pstmt.setInt(k, account.getRoleId());
            if (pstmt.executeUpdate() == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    long id = rs.getLong(1);
                    account.setId(id);
                } else {
                    throw new DaoException("Can't save an account");
                }
            } else {
                throw new DaoException("Can't save an account");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't save an account", e);
        }
    }

    private String hashPassword(String passwordToHash) {
        return Hashing.sha256().hashString(passwordToHash, StandardCharsets.UTF_8).toString();
    }

    private Account toAccount(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Account account = new Account();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case SQL_ACCOUNT_ID:
                    account.setId(resultSet.getLong(i));
                    break;
                case SQL_ACCOUNT_FNAME:
                    account.setFirstName(resultSet.getString(i));
                    break;
                case SQL_ACCOUNT_LNAME:
                    account.setLastName(resultSet.getString(i));
                    break;
                case SQL_ACCOUNT_EMAIL:
                    account.setEmail(resultSet.getString(i));
                    break;
                case SQL_ACCOUNT_IS_ADMIN:
                    account.setAdmin(resultSet.getBoolean(i));
                    break;
                case SQL_ACCOUNT_ROLE_ID:
                    account.setRoleId(resultSet.getInt(i));
                    break;
                default:
                    // No operations
            }
        }
        return account;
    }

}
