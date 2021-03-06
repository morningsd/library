package edu.demian.web.controller.page.jsp.librarian;

import edu.demian.model.entity.BookStatus;
import edu.demian.model.entity.Reserve;
import edu.demian.service.BookService;
import edu.demian.service.ReserveService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@PageAccessor(allowedTo = {PageAccessorType.LIBRARIAN})
public class OrdersPage {

    private final ReserveService reserveService = ServiceFactory.getReserveService(ServiceFactoryType.DEFAULT);
    private final BookService bookService = ServiceFactory.getBookService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {
        List<Reserve> reserveList = reserveService.findAllActive();
        request.setAttribute("reserveList", reserveList);
    }

    private void subscribe(final HttpServletRequest request) {
        String bookIdStr = request.getParameter("book_id");
        String reserveIdStr = request.getParameter("reserve_id");
        String bookStatusStr = request.getParameter("book_status");

        long bookId = Long.parseLong(bookIdStr);
        long reserveId = Long.parseLong(reserveIdStr);
        BookStatus bookStatus = BookStatus.valueOf(bookStatusStr);
        int bookStatusId = bookStatus.getId();

        bookService.makeSubscription(bookId, bookStatusId);
        LocalDate startDate = LocalDate.now();
        LocalDate finalDate = startDate.plusMonths(1);
        reserveService.setStartDate(reserveId, startDate);
        reserveService.setFinalDate(reserveId, finalDate);

        throw new RedirectException("/jsp/librarian/orders");
    }

}
