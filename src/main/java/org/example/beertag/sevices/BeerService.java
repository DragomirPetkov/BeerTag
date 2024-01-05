package org.example.beertag.sevices;

import org.example.beertag.models.Beer;
import org.example.beertag.models.User;

import java.util.List;

public interface BeerService {
    List<Beer> getAll();

    Beer getById(int id);

    void createBeer(Beer beer);

    void update(Beer beer, User user);

    void delete(int id, User user);
}
