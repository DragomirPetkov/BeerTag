package org.example.beertag.sevices;

import org.example.beertag.exseptions.EntityDuplicateException;
import org.example.beertag.exseptions.EntityNotFoundException;
import org.example.beertag.models.Beer;
import org.example.beertag.repositories.BeerRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


public class BeerServiceImpl {

    private BeerRepositoryImpl repository;

    public BeerServiceImpl() {
        this.repository = new BeerRepositoryImpl();
    }

    public List<Beer> getAll() {
        return repository.getAll();
    }

    public Beer getById(int id) {
        return repository.getById(id);
    }

    public void createBeer(Beer beer) {
        boolean duplicateExists = true;

        try {
            repository.getByName(beer.getName());
        }catch (EntityNotFoundException e){
            duplicateExists = false;
        }
        if (duplicateExists){
            throw new EntityNotFoundException("Beer","name",beer.getName());
        }
        repository.create(beer);
    }

    public void update(Beer beer){
        boolean duplicateExists = true;

        try {
            Beer existingBeer = repository.getByName(beer.getName());
            if (existingBeer.getId() == beer.getId() ){
                duplicateExists = false;
            }
        }catch (EntityNotFoundException e){
            duplicateExists = false;
        }
        if (duplicateExists){
            throw  new EntityDuplicateException("Beer","name",beer.getName());
        }
        repository.update(beer);
    }

    public void delete(int id){
        repository.delete(id);
    }
}
