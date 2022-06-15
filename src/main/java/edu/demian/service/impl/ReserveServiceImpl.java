package edu.demian.service.impl;

import edu.demian.model.dao.ReserveDao;
import edu.demian.model.dao.impl.ReserveDaoImpl;
import edu.demian.model.entity.Reserve;
import edu.demian.service.ReserveService;

public final class ReserveServiceImpl implements ReserveService {

    private final ReserveDao reserveDao = new ReserveDaoImpl();

    @Override
    public void save(final Reserve reserve) {
        reserveDao.save(reserve);
    }
}
