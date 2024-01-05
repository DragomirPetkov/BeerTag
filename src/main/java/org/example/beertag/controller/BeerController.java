package org.example.beertag.controller;

import jakarta.validation.Valid;
import org.example.beertag.exseptions.EntityDuplicateException;
import org.example.beertag.exseptions.EntityNotFoundException;
import org.example.beertag.exseptions.UnauthorizedOperationException;
import org.example.beertag.models.Beer;
import org.example.beertag.models.User;
import org.example.beertag.sevices.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/beers")
public class BeerController {

    private final BeerService service;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public BeerController(BeerService service, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.authenticationHelper = authenticationHelper;
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
    public Beer create(@RequestHeader HttpHeaders headers, @Valid @RequestBody Beer beer) {
        try {

            User user = authenticationHelper.tryGetUser(headers);
            service.createBeer(beer);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return beer;
    }

    @PutMapping("/{id}")
    public Beer update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody Beer newBeer) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.update(newBeer, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        return newBeer;
    }


    @DeleteMapping("/{id}")
    private void deleteBeer(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (UnauthorizedOperationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());
        }
    }


}
