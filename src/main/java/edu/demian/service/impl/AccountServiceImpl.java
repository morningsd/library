package edu.demian.service.impl;

import edu.demian.model.dao.AccountDao;
import edu.demian.model.dao.factory.DaoFactory;
import edu.demian.model.dao.factory.DaoFactoryType;
import edu.demian.model.entity.Account;
import edu.demian.service.AccountService;

import java.util.List;

public final class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao = DaoFactory.getAccountDao(DaoFactoryType.POSTGRESQL);

    public void saveLibrarian(final Account account, final String password) {
        accountDao.save(account, password);
    }

    public void block(final Long id) {
        accountDao.block(id);
    }

    @Override
    public void delete(final Long id) {
        accountDao.delete(id);
    }

    @Override
    public Account findByEmailAndPassword(final String email, final String password) {
        return accountDao.findByEmailAndPassword(email, password);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public List<Account> findAllLibrarians() {
        return accountDao.findAllLibrarians();
    }

    @Override
    public List<Account> findAllReaders() {
        return accountDao.findAllReaders();
    }

    @Override
    public void save(final Account account, final String password) {
        accountDao.save(account, password);
    }

    @Override
    public void unblock(final Long id) {
        accountDao.unblock(id);
    }

    @Override
    public Account find(Long id) {
        return accountDao.find(id);
    }

}
