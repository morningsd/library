package edu.demian.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {

    private static DBManager instance;

    private DataSource dataSource;

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            dataSource = (DataSource) envContext.lookup("jdbc/library");
        } catch (NamingException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return con;
    }

    public void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception ignored) {}
        }
    }

    public void rollbackClose(Connection con) {
        try {
            if (con != null) {
                con.rollback();
                con.close();
            }
        } catch (SQLException ignored) {}
    }

    public void commitClose(Connection con) {
        try {
            if (con != null) {
                con.commit();
                con.close();
            }
        } catch (SQLException ignored) {}
    }

}
