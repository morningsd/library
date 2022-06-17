package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Reserve;
import edu.demian.service.AccountService;
import edu.demian.service.ReserveService;
import edu.demian.service.impl.AccountServiceImpl;
import edu.demian.service.impl.ReserveServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SubscriptionsPageAction extends Action {

    private final ReserveService reserveService = new ReserveServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String readerIdStr = request.getParameter("reader_id");
        Long readerId = Long.parseLong(readerIdStr);

        Account reader = accountService.find(readerId);
        List<Reserve> reserveList = reserveService.findAllForUser(readerId);

        request.setAttribute("reader", reader);
        request.setAttribute("reserveList", reserveList);

        return "/librarian/subscriptions";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This URL does not support POST method");
    }
}
