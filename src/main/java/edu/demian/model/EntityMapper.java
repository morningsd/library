package edu.demian.model;

import java.sql.ResultSet;

public interface EntityMapper<T> {

    T mapRow(ResultSet rs);

}
