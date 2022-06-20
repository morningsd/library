package edu.demian.model.dao.factory;

import edu.demian.model.dao.AccountDao;
import edu.demian.model.dao.BookDao;
import edu.demian.model.dao.ReserveDao;
import edu.demian.model.dao.impl.AccountDaoImpl;
import edu.demian.model.dao.impl.BookDaoImpl;
import edu.demian.model.dao.impl.ReserveDaoImpl;
import edu.demian.model.exception.DaoException;

public class DaoFactory {

    public static AccountDao getAccountDao(String type) {
        if (DaoFactoryType.POSTGRESQL.equals(type)) {
            return new AccountDaoImpl();
        }
        throw new DaoException("Can't get suitable account DAO class for type:" + type);
    }

    public static BookDao getBookDao(String type) {
        if (DaoFactoryType.POSTGRESQL.equals(type)) {
            return new BookDaoImpl();
        }
        throw new DaoException("Can't get suitable book DAO class for type:" + type);
    }

    public static ReserveDao getReserveDao(String type) {
        if (DaoFactoryType.POSTGRESQL.equals(type)) {
            return new ReserveDaoImpl();
        }
        throw new DaoException("Can't get suitable reserve DAO class for type:" + type);
    }

}
