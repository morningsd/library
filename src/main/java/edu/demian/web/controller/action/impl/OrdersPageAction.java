package edu.demian.web.controller.action.impl;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.web.controller.action.ActionException;
import edu.demian.model.entity.Reserve;
import edu.demian.model.service.ReserveService;
import edu.demian.model.service.factory.ServiceFactory;
import edu.demian.model.service.factory.ServiceFactoryType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@PageAccessor(allowedTo = {PageAccessorType.LIBRARIAN})
public class OrdersPageAction extends Action {

    private final ReserveService reserveService = ServiceFactory.getReserveService(ServiceFactoryType.DEFAULT);

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        List<Reserve> reserveList = reserveService.findAllActive();
        request.setAttribute("reserveList", reserveList);

        return "/librarian/orders";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This URL does not support POST method");
    }
}
