package edu.demian.web.controller.action.impl;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.web.controller.action.ActionException;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Reserve;
import edu.demian.model.service.ReserveService;
import edu.demian.model.service.factory.ServiceFactory;
import edu.demian.model.service.factory.ServiceFactoryType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@PageAccessor(allowedTo = {PageAccessorType.READER, PageAccessorType.ADMINISTRATOR})
public final class OrderBookAction extends Action {

    private final ReserveService reserveService = ServiceFactory.getReserveService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support GET method");
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) throws ActionException {
        final HttpSession session = request.getSession();
        final Account account = (Account) session.getAttribute("account");

        final String bookId = request.getParameter("book_id");

        final Reserve reserve = new Reserve();
        reserve.setAccountId(account.getId());
        reserve.setBookId(Long.parseLong(bookId));
        reserve.setFinalDate(LocalDate.now().plusMonths(1));

        reserveService.save(reserve);

        return "redirect:/cabinet";
    }
}
