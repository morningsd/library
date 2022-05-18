package edu.demian.model.dao;

import edu.demian.model.entity.Account;

import java.util.List;

public interface AccountDao {

    Account find(Long id);

    Account findByEmailAndPassword(String email, String password);

    List<Account> findAll();

    List<Account> findAllLibrarians();

    void save(Account account, String password);

    void delete(Long id);

    void block(Long accountId);

    void unblock(Long accountId);



}
