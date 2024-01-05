package org.example.beertag.repositories;

import org.example.beertag.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();
    User getById(int id);
    User getByUsername(String username);
}
