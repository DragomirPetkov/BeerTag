package org.example.beertag.sevices;

import org.example.beertag.exseptions.EntityDuplicateException;
import org.example.beertag.exseptions.EntityNotFoundException;
import org.example.beertag.models.Beer;
import org.example.beertag.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerServiceImpl implements BeerService {

    private BeerRepository repository;
    @Autowired
    public BeerServiceImpl(BeerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Beer> getAll() {
        return repository.getAll();
    }

    @Override
    public Beer getById(int id) {
        return repository.getById(id);
    }

    @Override
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

    @Override
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

    @Override
    public void delete(int id){
        repository.delete(id);
    }
}
