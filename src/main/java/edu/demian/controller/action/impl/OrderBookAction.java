package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.dao.impl.ReserveDaoImpl;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Reserve;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderBookAction extends Action {

    private final ReserveDaoImpl reserveDAO = new ReserveDaoImpl();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support GET method");
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        String bookId = request.getParameter("book_id");

        Reserve reserve = new Reserve();
        reserve.setAccountId(account.getId());
        reserve.setBookId(Long.parseLong(bookId));

        reserveDAO.save(reserve);

        return "redirect:/cabinet";
    }
}
