package edu.demian.service;

import edu.demian.model.entity.Reserve;

import java.time.LocalDate;
import java.util.List;

public interface ReserveService {

    void save(Reserve reserve);

    List<Reserve> findAllActiveForUser(long id);

    List<Reserve> findAllForUser(long id);

    List<Reserve> findAllActive();

    void returnBook(long reserveId, long bookId, LocalDate submittedDate);

    void setStartDate(long reserveId, LocalDate now);

    void setFinalDate(long reserveId, LocalDate finalDate);
}
