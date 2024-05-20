package ru.kinoposisk.service.interfaces;

import java.util.List;

public interface BaseService<T> {

    T add(T t);
    void deleteByID(Long id);
    T findByID(Long id);
    List<T> getAll();
}
