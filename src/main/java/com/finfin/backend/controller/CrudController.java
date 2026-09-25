package com.finfin.backend.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudController<T, I, R> {

    public ResponseEntity<R> create(T request);
    public ResponseEntity<R> get(I identifier);
    public ResponseEntity<R> update(I identifier, T request);
    public ResponseEntity<String> delete(I identifier);

}
