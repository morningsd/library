package edu.demian.service;

import edu.demian.model.entity.Account;

import java.util.List;

public interface AccountService {

    void saveLibrarian(Account account, String password);

    void block(long id);

    void delete(long id);

    Account findByEmailAndPassword(String email, String password);

    List<Account> findAll();

    List<Account> findAllLibrarians();

    List<Account> findAllReaders();

    void save(Account account, String password);

    void unblock(long id);

    Account find(long id);
}
