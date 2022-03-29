package com.bsuir.websurvey.controllers;

import com.bsuir.websurvey.models.User_Pevneva;
import com.bsuir.websurvey.services.UserService_Pevneva;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController_Pevneva {
    @Autowired
    private UserService_Pevneva userService;

    @PostMapping("/register")
    public ResponseEntity<?>  Register(@RequestBody User_Pevneva user) {
        if (userService.UserExist(user.getLogin())) return new ResponseEntity<>(false, HttpStatus.OK);
        userService.SaveUser(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/login_exist")
    public ResponseEntity<?> LoginExist(@RequestBody String login) {
        Boolean response = userService.UserExist(login);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody String request) throws JSONException {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String password = json.getString("password");
        Boolean response = userService.GetUserByLogin(login).getPassword().equals(password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}