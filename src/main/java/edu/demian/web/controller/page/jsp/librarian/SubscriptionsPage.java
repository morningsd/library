package edu.demian.web.controller.page.jsp.librarian;

import edu.demian.model.entity.Account;
import edu.demian.model.entity.BookStatus;
import edu.demian.model.entity.Reserve;
import edu.demian.service.AccountService;
import edu.demian.service.ReserveService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@PageAccessor(allowedTo = {PageAccessorType.LIBRARIAN})
public class SubscriptionsPage {

    private final ReserveService reserveService = ServiceFactory.getReserveService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {

    }

    private void returnBook(final HttpServletRequest request) {

        String bookIdStr = request.getParameter("book_id");
        String reserveIdStr = request.getParameter("reserve_id");

        long bookId = Long.parseLong(bookIdStr);
        long reserveId = Long.parseLong(reserveIdStr);

        reserveService.returnBook(reserveId, bookId, LocalDate.now());

        throw new RedirectException("/jsp/librarian/readers");
    }

}
