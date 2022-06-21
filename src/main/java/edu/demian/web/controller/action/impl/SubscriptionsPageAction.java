package edu.demian.web.controller.action.impl;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.web.controller.action.ActionException;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Reserve;
import edu.demian.model.service.AccountService;
import edu.demian.model.service.ReserveService;
import edu.demian.model.service.factory.ServiceFactory;
import edu.demian.model.service.factory.ServiceFactoryType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@PageAccessor(allowedTo = {PageAccessorType.LIBRARIAN})
public class SubscriptionsPageAction extends Action {

    private final ReserveService reserveService = ServiceFactory.getReserveService(ServiceFactoryType.DEFAULT);
    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

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
