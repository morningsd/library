package edu.demian.model.dao;

import edu.demian.model.entity.Reserve;

import java.time.LocalDate;
import java.util.List;

public interface ReserveDao {

    void save(Reserve reserve);

    List<Reserve> findAllActiveForUser(long id);

    List<Reserve> findAllForUser(long id);

    List<Reserve> findAllActive();

    void setActive(long id, boolean isActive);

    void setStartDate(long reserveId, LocalDate now);

    void setFinalDate(long reserveId, LocalDate finalDate);

    void setSubmittedDate(long reserveId, LocalDate submittedDate);
}
