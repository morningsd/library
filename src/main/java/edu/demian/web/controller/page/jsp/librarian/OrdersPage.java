package edu.demian.web.controller.page.jsp.librarian;

import edu.demian.model.entity.Reserve;
import edu.demian.service.ReserveService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@PageAccessor(allowedTo = {PageAccessorType.LIBRARIAN})
public class OrdersPage {

    private final ReserveService reserveService = ServiceFactory.getReserveService(ServiceFactoryType.DEFAULT);

    private void action(final HttpServletRequest request) {
        List<Reserve> reserveList = reserveService.findAllActive();
        request.setAttribute("reserveList", reserveList);
    }

}
