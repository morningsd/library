package edu.demian.web.controller.action.impl;

import edu.demian.web.annotation.PageAccessor;
import edu.demian.web.annotation.PageAccessorType;
import edu.demian.web.controller.action.Action;
import edu.demian.web.controller.action.ActionException;
import edu.demian.model.entity.BookStatus;
import edu.demian.model.service.BookService;
import edu.demian.model.service.ReserveService;
import edu.demian.model.service.factory.ServiceFactory;
import edu.demian.model.service.factory.ServiceFactoryType;
import edu.demian.model.service.impl.BookServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@PageAccessor(allowedTo = {PageAccessorType.LIBRARIAN})
public class ManageOrdersPageAction extends Action {

    private final ReserveService reserveService = ServiceFactory.getReserveService(ServiceFactoryType.DEFAULT);
    private final BookService bookService = new BookServiceImpl();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        throw new UnsupportedOperationException("This url does not support GET method");
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        String bookIdStr = request.getParameter("book_id");
        String bookStatusStr = request.getParameter("book_status");

        Long bookId = Long.parseLong(bookIdStr);
        BookStatus bookStatus = BookStatus.valueOf(bookStatusStr);

        bookService.setStatus(bookId, bookStatus.getId());

        return "redirect:/librarian/orders";
    }
}
