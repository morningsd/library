package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.AccountDaoImpl;
import edu.demian.model.entity.Account;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public final class ManageLibrariansAction extends Action {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        final HttpSession session = request.getSession();

        final List<Account> librarianList = accountService.findAllLibrarians();

        session.setAttribute("librarianList", librarianList);

        return "/admin/manageLibrarians";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}
