package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.model.dao.impl.BookDaoImpl;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Book;
import edu.demian.model.entity.Role;
import edu.demian.service.BookService;
import edu.demian.service.impl.BookServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public final class CabinetPageAction extends Action {

    private final BookService bookService = new BookServiceImpl();

    @Override
    protected String doGet(final HttpServletRequest request, final HttpServletResponse response) {
        final HttpSession session = request.getSession();

        final Account account = (Account) session.getAttribute("account");
        final Role accountRole = (Role) session.getAttribute("accountRole");
        if ("READER".equalsIgnoreCase(accountRole.toString())) {
            final List<Book> bookList = bookService.findAllForUser(account.getId());
            session.setAttribute("bookList", bookList);
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
