package edu.demian.web.controller.page.jsp.librarian;

import edu.demian.model.entity.Account;
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
import java.util.List;

@PageAccessor(allowedTo = {PageAccessorType.LIBRARIAN})
public class ReadersPage {

    private final ReserveService reserveService = ServiceFactory.getReserveService(ServiceFactoryType.DEFAULT);
    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        Account reader = (Account) session.getAttribute("reader");

        if (reader != null) {
            long readerId = reader.getId();
            List<Reserve> reserveList = reserveService.findAllForUser(readerId);

            session.removeAttribute("reader");
            session.setAttribute("reserveList", reserveList);

            throw new RedirectException("/jsp/librarian/subscriptions");
        }

    }

    private void subscriptions(final HttpServletRequest request) {
        String readerIdStr = request.getParameter("reader_id");
        long readerId = Long.parseLong(readerIdStr);

        Account reader = accountService.find(readerId);
        List<Reserve> reserveList = reserveService.findAllForUser(readerId);

        HttpSession session = request.getSession();
        session.setAttribute("reader", reader);
        session.setAttribute("reserveList", reserveList);

        throw new RedirectException("/jsp/librarian/subscriptions");
    }

}
