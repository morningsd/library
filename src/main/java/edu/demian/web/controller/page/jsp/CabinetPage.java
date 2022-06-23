package edu.demian.web.controller.page.jsp;

import edu.demian.model.entity.Account;
import edu.demian.model.entity.Reserve;
import edu.demian.model.entity.Role;
import edu.demian.service.AccountService;
import edu.demian.service.ReserveService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@PageAccessor(allowedTo = {PageAccessorType.READER, PageAccessorType.LIBRARIAN, PageAccessorType.ADMINISTRATOR})
public class CabinetPage {

    private final ReserveService reserveService = ServiceFactory.getReserveService(ServiceFactoryType.DEFAULT);
    private final AccountService accountService = ServiceFactory.getAccountService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {
        final HttpSession session = request.getSession();

        final Account account = (Account) session.getAttribute("account");
        final Role accountRole = (Role) session.getAttribute("accountRole");
        if (Role.READER.getName().equalsIgnoreCase(accountRole.toString())) {
            final List<Reserve> reserveList = reserveService.findAllActiveForUser(account.getId());
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
        if (Role.LIBRARIAN.getName().equalsIgnoreCase(accountRole.toString())) {
            // list of users' orders
            // list of users + abonements
            List<Account> readerList = accountService.findAllReaders();
            session.setAttribute("readerList", readerList);
        }
    }

}
