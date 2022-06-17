package edu.demian.model.dao.impl;

import com.google.common.hash.Hashing;
import edu.demian.model.DBManager;
import edu.demian.model.dao.AccountDao;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Role;
import edu.demian.model.exception.DaoException;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public final class AccountDaoImpl implements AccountDao {

    private static final DBManager DB_MANAGER_INSTANCE = DBManager.getInstance();

    private static final String SQL_ACCOUNT_ID = "id";
    private static final String SQL_ACCOUNT_FNAME = "first_name";
    private static final String SQL_ACCOUNT_LNAME = "last_name";
    private static final String SQL_ACCOUNT_EMAIL = "email";
    private static final String SQL_ACCOUNT_IS_ADMIN = "is_admin";
    private static final String SQL_ACCOUNT_IS_BLOCKED = "is_blocked";
    private static final String SQL_ACCOUNT_ROLE_ID = "role_id";


    private static final String SQL_FIND_ACCOUNT = "SELECT * FROM account WHERE id=?";
    private static final String SQL_FIND_ACCOUNT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM account WHERE email=?" +
            " AND password=? AND is_blocked=FALSE";
    private static final String SQL_FIND_ALL = "SELECT * FROM account";
    private static final String SQL_FIND_ALL_LIBRARIANS = "SELECT * FROM account WHERE role_id = (SELECT id FROM role WHERE name='LIBRARIAN')";
    private static final String SQL_FIND_ALL_READERS = "SELECT * FROM account WHERE role_id = (SELECT id FROM role WHERE name='READER')";
    private static final String SQL_DELETE_ACCOUNT = "DELETE FROM account WHERE id=?";
    private static final String SQL_BLOCK_ACCOUNT = "UPDATE account SET is_blocked=TRUE WHERE id=?";
    private static final String SQL_UNBLOCK_ACCOUNT = "UPDATE account SET is_blocked=FALSE WHERE id=?";
    private static final String SQL_INSERT_ACCOUNT = "INSERT INTO account " +
            "(first_name, last_name, email, password, is_admin, is_blocked, role_id) VALUES (?,?,?,?,?,?,?)";

    public Account find(final Long id) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = pstmt.getMetaData();
            return toAccount(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find an account", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }


    public Account findByEmailAndPassword(final String email, final String password) {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_EMAIL_AND_PASSWORD)) {
            pstmt.setString(1, email);
            pstmt.setString(2, hashPassword(password));
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = pstmt.getMetaData();
            return toAccount(metaData, rs);
        } catch (final SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }


    public List<Account> findAll() {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ALL)) {
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            return toAccountList(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find all users", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    public List<Account> findAllLibrarians() {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ALL_LIBRARIANS)) {
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            return toAccountList(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find all librarians", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }

    @Override
    public List<Account> findAllReaders() {
        ResultSet rs = null;
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ALL_READERS)) {
            rs = pstmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            return toAccountList(metaData, rs);
        } catch (final SQLException e) {
            throw new DaoException("Can't find all readers", e);
        } finally {
            DB_MANAGER_INSTANCE.close(rs);
        }
    }


    public void save(final Account account, final String password) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;
            pstmt.setString(k++, account.getFirstName());
            pstmt.setString(k++, account.getLastName());
            pstmt.setString(k++, account.getEmail());
            pstmt.setString(k++, hashPassword(password));
            final Boolean isAdmin = account.getAdmin();
            if (isAdmin != null) {
                pstmt.setBoolean(k++, isAdmin);
            } else {
                pstmt.setBoolean(k++, Boolean.FALSE);
            }
            final Boolean isBlocked = account.getBlocked();
            if (isBlocked != null) {
                pstmt.setBoolean(k++, isBlocked);
            } else {
                pstmt.setBoolean(k++, Boolean.FALSE);
            }
            final Integer roleId = account.getRoleId();
            if (roleId != null) {
                pstmt.setInt(k, roleId);
            } else {
                pstmt.setInt(k, Role.READER.ordinal());
            }
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't save an account");
            }
        } catch (final SQLException e) {
            throw new DaoException("Can't save an account", e);
        }
    }

    public void delete(final Long id) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_ACCOUNT)) {
            pstmt.setLong(1, id);
            if (pstmt.executeUpdate() != 1) {
                throw new DaoException("Can't delete an account");
            }
        } catch (final SQLException e) {
            throw new DaoException("Can't delete an account", e);
        }
    }

    public void block(final Long accountId) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_BLOCK_ACCOUNT)) {
            pstmt.setLong(1, accountId);
            pstmt.executeUpdate();
        } catch (final SQLException e) {
            throw new DaoException("Can't block an account", e);
        }
    }


    public void unblock(final Long accountId) {
        try (Connection connection = DB_MANAGER_INSTANCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL_UNBLOCK_ACCOUNT)) {
            pstmt.setLong(1, accountId);
            pstmt.executeUpdate();
        } catch (final SQLException e) {
            throw new DaoException("Can't unblock an account", e);
        }
    }

    private String hashPassword(final String passwordToHash) {
        return Hashing.sha256().hashString(passwordToHash, StandardCharsets.UTF_8).toString();
    }

    private List<Account> toAccountList(final ResultSetMetaData metaData, final ResultSet resultSet) throws SQLException {
        final List<Account> accountList = new LinkedList<>();

        while (true) {
            final Account account = toAccount(metaData, resultSet);
            if (account == null) {
                break;
            }
            accountList.add(account);
        }

        return accountList;
    }

    private Account toAccount(final ResultSetMetaData metaData, final ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        final Account account = new Account();

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
                case SQL_ACCOUNT_IS_BLOCKED:
                    account.setBlocked(resultSet.getBoolean(i));
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
