package org.example.beertag.controller;

import org.example.beertag.models.Beer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/beers")
public class BeerController {

    private final List<Beer> beers;

    public BeerController(){
        this.beers = new ArrayList<>();

        beers.add(new Beer(1,"Zagorka",4.5));
        beers.add(new Beer(2,"Shumensko",4.4));
        beers.add(new Beer(3,"Kamenitca",4.6));
    }

    @GetMapping
    private List<Beer> getBeers(){
        return beers;
    }
    @GetMapping("/{id}")
    private Beer getBeerById(@PathVariable int id){
       return getSingleBeerById(id);
    }



    @DeleteMapping("/{id}")
    private void deleteBeer(@PathVariable int id){
       beers.remove(getBeerById(id));
    }

    private Beer getSingleBeerById(int id) {
        return beers.stream()
                .filter(beer -> beer.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Beer with id %d not found.",id)));
    }
}