package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.ReserveDaoImpl;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Reserve;
import edu.demian.service.ReserveService;
import edu.demian.service.impl.ReserveServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

public final class OrderBookAction extends Action {

    private final ReserveService reserveService = new ReserveServiceImpl();

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
