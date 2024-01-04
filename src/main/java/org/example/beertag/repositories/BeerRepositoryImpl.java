package org.example.beertag.repositories;

import org.example.beertag.exseptions.EntityNotFoundException;
import org.example.beertag.models.Beer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


public class BeerRepositoryImpl {

    private List<Beer> beers;

    public BeerRepositoryImpl() {
        this.beers = new ArrayList<>();

        beers.add(new Beer(1, "Zagorka", 4.5));
        beers.add(new Beer(2, "Shumensko", 4.4));
        beers.add(new Beer(3, "Kamenitca", 4.6));
    }

    public List<Beer> getAll(){
        return beers;
    }

    public Beer getById(int id){
        return beers.stream()
                .filter(beer -> beer.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Beer",id));
    }

    public Beer getByName(String name){
        return beers.stream()
                .filter(beer -> beer.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Beer","name",name));
    }

    public void create(Beer beer){
        beers.add(beer);
    }

    public void update(Beer beer){
        Beer beerToUpdate = getById(beer.getId());
        beerToUpdate.setName(beer.getName());
        beerToUpdate.setAbv(beer.getAbv());
    }

    public void delete(int id){
        Beer beerToDelete = getById(id);
        beers.remove(beerToDelete);
    }
}
