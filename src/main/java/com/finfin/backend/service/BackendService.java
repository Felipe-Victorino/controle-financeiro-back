package com.finfin.backend.service;

import java.util.List;

public interface BackendService<T, E> {
    T findById(E id);
    T insert(T t);
    void delete(E id);

    T update(T t);
    List<T> listAll();
}
