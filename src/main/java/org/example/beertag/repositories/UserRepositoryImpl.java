package org.example.beertag.repositories;

import org.example.beertag.exseptions.EntityNotFoundException;
import org.example.beertag.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users;

    public UserRepositoryImpl(){
        this.users = new ArrayList<>();

        users.add(new User(1,"asen",true));
        users.add(new User(2,"ivan",false));
        users.add(new User(3,"pesho",false));
        users.add(new User(4,"asq",false));
    }
    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User getById(int id) {
        return getAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User",id));
    }

    @Override
    public User getByUsername(String username) {
        return getAll().stream()
                .filter(user -> user.getUserName().equals(username))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User","username",username));
    }
}
