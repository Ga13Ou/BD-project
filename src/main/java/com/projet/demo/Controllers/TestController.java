package com.projet.demo.Controllers;


import com.projet.demo.Models.User;
import com.projet.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    UserService userService;
@GetMapping("/")
public String Hello(){
    System.out.println("application is working!");
    return "application is working";
}
    @GetMapping("/{id}")
    public Map<String, Object> testGet(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PostMapping("/add")
    public User testAdd(@RequestBody User user){
        userService.addUserForTest(user);
        return user;
    }
}
