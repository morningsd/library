package edu.demian.service.factory;

import edu.demian.service.AccountService;
import edu.demian.service.BookService;
import edu.demian.service.exception.ServiceException;
import edu.demian.service.impl.AccountServiceImpl;
import edu.demian.service.impl.BookServiceImpl;
import edu.demian.service.impl.ReserveServiceImpl;
import edu.demian.service.ReserveService;

public class ServiceFactory {

    public static AccountService getAccountService(String type) {
        if (ServiceFactoryType.DEFAULT.equals(type)) {
            return new AccountServiceImpl();
        }
        throw new ServiceException("Can't get suitable account service class for type:" + type);
    }

    public static BookService getBookService(String type) {
        if (ServiceFactoryType.DEFAULT.equals(type)) {
            return new BookServiceImpl();
        }
        throw new ServiceException("Can't get suitable book service class for type:" + type);
    }

    public static ReserveService getReserveService(String type) {
        if (ServiceFactoryType.DEFAULT.equals(type)) {
            return new ReserveServiceImpl();
        }
        throw new ServiceException("Can't get suitable reserve service class for type:" + type);
    }

}
