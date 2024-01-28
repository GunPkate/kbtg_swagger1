package com.example.swagger.controller;

import com.example.swagger.service.UserService;
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

        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }//Constructor

    @GetMapping("/api/users/{active}")

        public List<User> getUsers(@PathVariable("active") Optional<Boolean> active){
        return this.userService.getUsers(active);
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
