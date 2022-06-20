package edu.demian.model.dao;

import edu.demian.model.entity.Account;

import java.util.List;

public interface AccountDao {

    Account find(Long id);

    void save(Account account, String password);

    void delete(Long id);

    List<Account> findAll();

    Account findByEmailAndPassword(String email, String password);

    List<Account> findAllLibrarians();

    List<Account> findAllReaders();

    void block(Long accountId);

    void unblock(Long accountId);


}
