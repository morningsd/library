package edu.demian.web.controller.page.jsp.admin;

import edu.demian.model.entity.Account;
import edu.demian.service.AccountService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@PageAccessor(allowedTo = {PageAccessorType.ADMINISTRATOR})
public class ManageAccountsPage {

    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final List<Account> accountList = accountService.findAll();
        session.setAttribute("accountList", accountList);
    }

    private void unblock(final HttpServletRequest request) {
        final String accountIdStr = request.getParameter("account_id");
        final long accountId = Long.parseLong(accountIdStr);

        accountService.unblock(accountId);

        throw new RedirectException("/jsp/admin/manageAccounts");
    }

    private void block(final HttpServletRequest request) {
        final String accountIdStr = request.getParameter("account_id");
        final long accountId = Long.parseLong(accountIdStr);

        accountService.block(accountId);

        throw new RedirectException("/jsp/admin/manageAccounts");
    }
}
