package com.example.swagger.service;

import com.example.swagger.controller.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    List<User> users = new ArrayList<>(
            List.of(
                    new User(1,"A",19,true),
                    new User(2,"B",22,false),
                    new User(3,"C",21,true)
            )
    );

    public List<User> getUsers(@PathVariable("active") Optional<Boolean> active){
        if(active.isPresent()){
            return users.stream().filter(u->u.getActive() == active.get()).toList();
        }
        return users;
    }
}
