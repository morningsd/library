package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.controller.action.ActionException;
import edu.demian.model.entity.Reserve;
import edu.demian.service.ReserveService;
import edu.demian.service.factory.ServiceFactory;
import edu.demian.service.factory.ServiceFactoryType;
import edu.demian.service.impl.ReserveServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
