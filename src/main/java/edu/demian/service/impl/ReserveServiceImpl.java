package edu.demian.service.impl;

import edu.demian.model.dao.BookDao;
import edu.demian.model.dao.ReserveDao;
import edu.demian.model.dao.factory.DaoFactory;
import edu.demian.model.dao.factory.DaoFactoryType;
import edu.demian.model.entity.BookStatus;
import edu.demian.model.entity.Reserve;
import edu.demian.service.ReserveService;

import java.util.List;

public class ReserveServiceImpl implements ReserveService {

    private final ReserveDao reserveDao = DaoFactory.getReserveDao(DaoFactoryType.POSTGRESQL);
    private final BookDao bookDao = DaoFactory.getBookDao(DaoFactoryType.POSTGRESQL);

    @Override
    public void save(final Reserve reserve) {
        reserveDao.save(reserve);
    }

    @Override
    public List<Reserve> findAllActiveForUser(long id) {
        return reserveDao.findAllActiveForUser(id);
    }

    @Override
    public List<Reserve> findAllForUser(long id) {
        return reserveDao.findAllForUser(id);
    }

    @Override
    public List<Reserve> findAllActive() {
        return reserveDao.findAllActive();
    }

    @Override
    public void returnBook(long reserveId, long bookId) {
        reserveDao.setActive(reserveId, false);
        bookDao.setStatus(bookId, BookStatus.IN_STOCK.getId());
    }
}
