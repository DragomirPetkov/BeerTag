package org.example.beertag.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Negative;
import org.example.beertag.exseptions.EntityDuplicateException;
import org.example.beertag.exseptions.EntityNotFoundException;
import org.example.beertag.models.Beer;
import org.example.beertag.repositories.BeerRepositoryImpl;
import org.example.beertag.sevices.BeerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/beers")
public class BeerController {

    private BeerServiceImpl service;

    public BeerController() {
        this.service = new BeerServiceImpl();
    }

    @GetMapping
    private List<Beer> getBeers() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    private Beer getBeerById(@PathVariable int id) {
        try {
            return service.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage());
        }
    }

    @PostMapping
    public Beer create(@Valid @RequestBody Beer beer) {
        try {
        service.createBeer(beer);

        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
        }
        return beer;
    }

    @PutMapping("/{id}")
    public Beer update(@PathVariable int id, @Valid @RequestBody Beer newBeer) {
        try{
            service.update(newBeer);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }catch (EntityDuplicateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
        }

        return newBeer;
    }


    @DeleteMapping("/{id}")
    private void deleteBeer(@PathVariable int id) {
        try {
            service.delete(id);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }


}
