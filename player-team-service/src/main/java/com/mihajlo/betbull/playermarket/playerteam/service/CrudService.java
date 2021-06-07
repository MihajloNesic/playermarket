package com.mihajlo.betbull.playermarket.playerteam.service;

import java.util.List;

public interface CrudService<E, ID> {
    List<E> getAll();
    E getById(ID id);
    E save(E object);
    void delete(E object);
    void deleteById(ID id);
}