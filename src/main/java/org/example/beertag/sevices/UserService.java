package org.example.beertag.sevices;

import org.example.beertag.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getById(int id);
    User getByUsername(String username);
}
