package edu.demian.service.impl;

import edu.demian.model.dao.ReserveDao;
import edu.demian.model.dao.factory.DaoFactory;
import edu.demian.model.dao.factory.DaoFactoryType;
import edu.demian.model.entity.Reserve;
import edu.demian.service.ReserveService;

import java.util.List;

public final class ReserveServiceImpl implements ReserveService {

    private final ReserveDao reserveDao = DaoFactory.getReserveDao(DaoFactoryType.POSTGRESQL);

    @Override
    public void save(final Reserve reserve) {
        reserveDao.save(reserve);
    }

    @Override
    public List<Reserve> findAllActiveForUser(Long id) {
        return reserveDao.findAllActiveForUser(id);
    }

    @Override
    public List<Reserve> findAllForUser(Long id) {
        return reserveDao.findAllForUser(id);
    }

    @Override
    public List<Reserve> findAllActive() {
        return reserveDao.findAllActive();
    }
}
