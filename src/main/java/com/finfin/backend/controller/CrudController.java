package com.finfin.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CrudController<T, I, R> {

    public ResponseEntity<R> create(T request, Authentication auth);

    public ResponseEntity<R> get(I identifier, Authentication auth);

    public ResponseEntity<R> update(I identifier, T request, Authentication auth);

    public ResponseEntity<String> delete(I identifier, Authentication auth);

}
