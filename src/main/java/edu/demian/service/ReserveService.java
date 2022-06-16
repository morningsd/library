package edu.demian.service;

import edu.demian.model.entity.Reserve;

import java.util.List;

public interface ReserveService {

    void save(Reserve reserve);

    List<Reserve> findAllForUser(Long id);
}
