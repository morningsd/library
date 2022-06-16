package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.model.dao.impl.BookDaoImpl;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Book;
import edu.demian.model.entity.Reserve;
import edu.demian.model.entity.Role;
import edu.demian.service.BookService;
import edu.demian.service.ReserveService;
import edu.demian.service.impl.BookServiceImpl;
import edu.demian.service.impl.ReserveServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public final class CabinetPageAction extends Action {

    private final ReserveService reserveService = new ReserveServiceImpl();

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        final HttpSession session = request.getSession();

        final Account account = (Account) session.getAttribute("account");
        final Role accountRole = (Role) session.getAttribute("accountRole");
        if ("READER".equalsIgnoreCase(accountRole.toString())) {
            final List<Reserve> reserveList = reserveService.findAllForUser(account.getId());
            session.setAttribute("reserveList", reserveList);
            for (Reserve reserve: reserveList) {
                final LocalDate createdDate = reserve.getCreatedDate();
                final LocalDate finalDate = reserve.getFinalDate();
                if (createdDate.isAfter(finalDate)) {
                    long overdue = DAYS.between(createdDate, finalDate);
                    // TODO set fine from the properties file
                    reserve.setFine(BigDecimal.valueOf(overdue * 1.5));
                }
            }
        }
        if ("LIBRARIAN".equalsIgnoreCase(accountRole.toString())) {
            // list of users' orders
            // list of users + abonements
        }

        return "/cabinet";
    }

    @Override
    protected String doPost(final HttpServletRequest request, final HttpServletResponse response) {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}
