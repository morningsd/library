package edu.demian.model.service;

import edu.demian.model.entity.Reserve;

import java.util.List;

public interface ReserveService {

    void save(Reserve reserve);

    List<Reserve> findAllActiveForUser(Long id);

    List<Reserve> findAllForUser(Long id);

    List<Reserve> findAllActive();
}
