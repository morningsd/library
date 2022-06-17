package edu.demian.model.dao;

import edu.demian.model.entity.Reserve;

import java.util.List;

public interface ReserveDao {

    void save(Reserve reserve);

    List<Reserve> findAllActiveForUser(Long id);

    List<Reserve> findAllForUser(Long id);

    List<Reserve> findAllActive();
}
