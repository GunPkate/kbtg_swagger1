package com.example.swagger.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
//    @RequestMapping("/api/users")
public class UserController {
//        @GetMapping
//        public  ResponseEntity<List<String>> listUser(){
//            return new ResponseEntity<>( List.of("aa","bb"), HttpStatus.OK);
//        }
//
//        @PostMapping
////        @ApiResponse(responseCode = "201",description = "User created by GP")
//        public  ResponseEntity<Integer> createUser(Integer id,String name){
//            return new ResponseEntity<>(id,HttpStatus.CREATED);
//        }

        List<User> users = new ArrayList<>(
             List.of(
              new User(1,"A",19,true),
              new User(2,"B",22,false),
              new User(3,"C",21,true)
             )
        );
        // List of is inmutable
        @GetMapping("/api/users/{active}")

        public List<User> getUsers(@PathVariable("active") Optional<Boolean> active){
          if(active.isPresent()){
              return users.stream().filter(u->u.getActive() == active.get()).toList();
          }
          return users;
        }

        @PostMapping("/api/users")
        public User createUser(@RequestBody UserRequest request){
            Optional<Integer> maxId = users.stream().map(User::getId).max(Integer::compareTo);

            int nextId = maxId.orElse(0)+1;
            System.out.print(nextId);
            User user = new User(nextId,request.name(),request.age(),true );
            users.add(user);
            return user;
        }

        @PutMapping("api/users/{id}")
        public void editUser(@PathVariable("id") int id, @RequestBody UserRequest request){
            Optional<User> user = users.stream().filter(u->u.getId() == id).findFirst();
            if(user.isPresent()){
                User u = user.get();
                u.setName(request.name());
                u.setAge(request.age());
            }
        }

        @DeleteMapping("api/users/{id}")
        public void deleteUser(@PathVariable("id") int id){
            users.removeIf(u->u.getId() == id);
        }
}

record UserRequest(String name, int age ) {}
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class User {
    private int id;
    private String name;
    private int age;
    private Boolean active;

    public User (int id,String name,int age, Boolean active){
        this.id = id;
        this.age = age;
        this.name = name;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
