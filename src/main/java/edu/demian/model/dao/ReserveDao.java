package edu.demian.model.dao;

import edu.demian.model.entity.Reserve;

import java.util.List;

public interface ReserveDao {

    void save(Reserve reserve);


    List<Reserve> findAllForUser(Long id);
}
