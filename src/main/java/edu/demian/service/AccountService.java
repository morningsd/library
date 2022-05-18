package edu.demian.service;

import edu.demian.model.entity.Account;

import javax.servlet.http.HttpServletRequest;

public interface AccountService {

    void saveLibrarian(Account account, String password);

    void block(Long id);

}
