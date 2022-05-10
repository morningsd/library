package edu.demian.model.dao;

import edu.demian.model.DBManager;
import edu.demian.model.EntityMapper;
import edu.demian.model.entity.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    private static final DBManager DB_MANAGER_INSTANCE = DBManager.getInstance();

    private static final String SQL_ACCOUNT_ID = "id";
    private static final String SQL_ACCOUNT_FNAME = "first_name";
    private static final String SQL_ACCOUNT_LNAME = "last_name";
    private static final String SQL_ACCOUNT_EMAIL = "email";
    private static final String SQL_ACCOUNT_PASS = "password";
    private static final String SQL_ACCOUNT_IS_ADMIN = "is_admin";
    private static final String SQL_ACCOUNT_ROLE_ID = "role_id";


    private static final String SQL_FIND_ACCOUNT = "SELECT * FROM account WHERE id=?";

    public Account find(long id) throws SQLException {
        Account account = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DB_MANAGER_INSTANCE.getConnection();
            AccountMapper mapper = new AccountMapper();
            pstmt = connection.prepareStatement(SQL_FIND_ACCOUNT);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                account = mapper.mapRow(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackClose(connection);
            e.printStackTrace();
        } finally {
            DBManager.getInstance().commitClose(connection);
        }
        return account;
    }

    private static class AccountMapper implements EntityMapper<Account> {

        public Account mapRow(ResultSet rs) {
            try {
                Account account = new Account();
                account.setId(rs.getLong(SQL_ACCOUNT_ID));
                account.setFirstName(rs.getString(SQL_ACCOUNT_FNAME));
                account.setLastName(rs.getString(SQL_ACCOUNT_LNAME));
                account.setEmail(rs.getString(SQL_ACCOUNT_EMAIL));
                account.setAdmin(rs.getBoolean(SQL_ACCOUNT_IS_ADMIN));
                account.setRoleId(rs.getInt(SQL_ACCOUNT_ROLE_ID));
                return account;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            } finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
