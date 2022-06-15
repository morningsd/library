package edu.demian.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class DBManager {

    private static DBManager instance;

    private DataSource dataSource;

    private DBManager() {
        try {
            final Context initContext = new InitialContext();
            final Context envContext = (Context) initContext.lookup("java:/comp/env");

            dataSource = (DataSource) envContext.lookup("jdbc/library");
        } catch (final NamingException e) {
            throw new RuntimeException("Can't retrieve data source", e);
        }
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Can't retrieve connection", e);
        }
        return con;
    }

    public void close(final AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception ignored) {}
        }
    }

    public void rollbackClose(final Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
                connection.close();
            }
        } catch (SQLException ignored) {}
    }

    public void commitClose(Connection connection) {
        try {
            if (connection != null) {
                connection.commit();
                connection.close();
            }
        } catch (SQLException ignored) {}
    }

}
