package org.example.beertag.repositories;

import org.example.beertag.exseptions.EntityNotFoundException;
import org.example.beertag.models.Beer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeerMapRepositoryImpl implements BeerRepository{

    private Map<Integer, Beer> beerMap;

    public BeerMapRepositoryImpl() {
        this.beerMap = new HashMap<>();
    }

    @Override
    public List<Beer> getAll() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getById(int id) {
        Beer beer = beerMap.get(id);

        if (beer == null){
            throw new EntityNotFoundException("Beer",id);
        }
        return beer;
    }

    @Override
    public Beer getByName(String name) {
        return beerMap.values().stream().filter(beer -> beer.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Beer","name",name));
    }

    @Override
    public void create(Beer beer) {
        beerMap.put(beer.getId(),beer);
    }

    @Override
    public void update(Beer beer) {
        beerMap.replace(beer.getId(),beer);
    }

    @Override
    public void delete(int id) {
        beerMap.remove(id);
    }
}
