package com.websurvey.websurvey_pevneva.controller_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.User_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.IUserService_Pevneva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController_Pevneva {
    @Autowired
    private IUserService_Pevneva userService;

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        User_Pevneva user = new User_Pevneva();
        user.setLogin(json.getString("login"));
        user.setRole(json.getBoolean("role"));
        user.setPasswordHash(String.valueOf(json.getString("password").hashCode()));
        user.setCodePhraseHash(String.valueOf(json.getString("code_phrase").hashCode()));

        if (userService.UserExist(user.getLogin())) return new ResponseEntity<>(false, HttpStatus.OK);
        userService.SaveUser(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/login_exist")
    public ResponseEntity<?> LoginExist(@RequestBody String login) {
        Boolean response = userService.UserExist(login);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify_session")
    public ResponseEntity<?> VerifySession(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String sessionIdHash = String.valueOf(json.getString("session_id").hashCode());

        Boolean response = userService.GetUserByLogin(login).getSessionIdHash().equals(sessionIdHash);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/remove_session")
    public ResponseEntity<?> RemoveSession(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String sessionId = String.valueOf(json.getString("session_id").hashCode());

        User_Pevneva user = userService.GetUserByLogin(login);
        if (!user.getSessionIdHash().equals(sessionId)) return new ResponseEntity<>(false, HttpStatus.OK);;

        userService.UpdateSessionIdHashById(user.getId(), null);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String password = String.valueOf(json.getString("password").hashCode());

        User_Pevneva user = userService.GetUserByLogin(login);
        if (!user.getPasswordHash().equals(password)) return new ResponseEntity<>(false, HttpStatus.OK);;

        String sessionId = String.valueOf(ThreadLocalRandom.current().nextInt());
        userService.UpdateSessionIdHashById(user.getId(), String.valueOf(sessionId.hashCode()));

        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }
}
