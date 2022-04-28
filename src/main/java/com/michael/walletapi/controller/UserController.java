package com.michael.walletapi.controller;

import com.michael.walletapi.model.User;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping()
    public List<User> getAllUser(){
        return this.userService.getAllUser();
    }

    @PostMapping()
    public User createUser(@RequestBody UserDTO userDTO){
        return this.userService.createUser(userDTO);
    }

    @DeleteMapping("/{id}")
    public void  deleteUser(@PathVariable Long id){
         this.userService.deleteUser(id);
    }

}
