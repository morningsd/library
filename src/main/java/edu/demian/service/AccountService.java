package edu.demian.service;

import edu.demian.model.entity.Account;

import java.util.List;

public interface AccountService {

    void saveLibrarian(Account account, String password);

    void block(Long id);

    void delete(Long id);

    Account findByEmailAndPassword(String email, String password);

    List<Account> findAll();

    List<Account> findAllLibrarians();

    void save(Account account, String password);

    void unblock(Long id);
}
