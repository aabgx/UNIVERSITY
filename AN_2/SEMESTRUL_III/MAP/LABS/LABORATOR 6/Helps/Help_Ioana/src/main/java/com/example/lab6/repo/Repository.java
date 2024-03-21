package com.example.lab6.repo;

import com.example.lab6.domain.Entity;

import java.util.List;

public interface Repository<Tip,E extends Entity<Tip>> {

    void save(E adding);

    void delete(E user1);

    E find(Tip userToBeFound);

    void update(E entity,Tip id);

    List<E> getList();
}
