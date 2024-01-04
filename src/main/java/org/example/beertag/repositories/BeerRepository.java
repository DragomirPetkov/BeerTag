package org.example.beertag.repositories;

import org.example.beertag.models.Beer;

import java.util.List;

public interface BeerRepository {
    List<Beer> getAll();

    Beer getById(int id);

    Beer getByName(String name);

    void create(Beer beer);

    void update(Beer beer);

    void delete(int id);
}
