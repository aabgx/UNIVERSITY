package com.example.tema_bun.repo;

import com.example.tema_bun.domain.Entitate;

import java.util.List;

public interface Repository<ID,entityType> {
    void adauga(entityType entity);
    void sterge(entityType entity);
    entityType cautaId(ID id);
    List<entityType> getAll();
    void update(ID id,entityType elem);
}

