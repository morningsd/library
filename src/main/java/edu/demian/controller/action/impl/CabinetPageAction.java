package edu.demian.controller.action.impl;

import edu.demian.controller.action.Action;
import edu.demian.model.dao.impl.BookDaoImpl;
import edu.demian.model.entity.Account;
import edu.demian.model.entity.Book;
import edu.demian.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CabinetPageAction extends Action {

    private final BookDaoImpl bookDAO = new BookDaoImpl();

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");
        Role accountRole = (Role) session.getAttribute("accountRole");
        if ("READER".equalsIgnoreCase(accountRole.toString())) {
            List<Book> bookList = bookDAO.findAllActiveForAccount(account.getId());

            session.setAttribute("bookList", bookList);
        }
        if ("LIBRARIAN".equalsIgnoreCase(accountRole.toString())) {

        }

        return "/cabinet";
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("This url does not support POST method");
    }
}
