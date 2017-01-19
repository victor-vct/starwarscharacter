package com.vctapps.starwarscharacters.persistence.dao;

import java.util.List;

/**
 * Created by Victor on 19/01/2017.
 */

public interface DAO<T> {

    T readById(int id);
    List<T> readAll();
    long save(T t);
    int update(T t);
    int delete(T t);
    int delete(int id);
}
