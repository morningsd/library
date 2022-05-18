package edu.demian.service.impl;

import edu.demian.model.dao.AccountDao;
import edu.demian.model.dao.impl.AccountDaoImpl;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Role;
import edu.demian.service.AccountService;

import javax.servlet.http.HttpServletRequest;

public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao = new AccountDaoImpl();

    public void saveLibrarian(Account account, String password) {
        accountDao.save(account, password);
    }


    public void block(Long id) {
        accountDao.block(id);
    }

}
