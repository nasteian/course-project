package com.websurvey.websurvey_pevneva.controller_pevneva;

import com.websurvey.websurvey_pevneva.enums_pevneva.UserRole_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.IUserService_Pevneva;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.*;
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
        UserModel_Pevneva user = new UserModel_Pevneva();
        user.setLogin(json.getString("login"));
        user.setRole(json.getInt("role"));
        user.setPasswordHash(String.valueOf(json.getString("password").hashCode()));
        user.setCodePhraseHash(String.valueOf(json.getString("code_phrase").hashCode()));

        if (userService.UserExist(user.getLogin())) return new ResponseEntity<>(false, HttpStatus.OK);
        userService.SaveUser(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/exist/{login}")
    public ResponseEntity<?> LoginExist(@PathVariable("login") String login) {
        Boolean response = userService.UserExist(login);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/surveys/uncompleted")
    public ResponseEntity<?> GetUncompletedSurveys(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!VerifySession(login, sessionId)) return new ResponseEntity<>(false, HttpStatus.OK);
        UserModel_Pevneva user = userService.GetUserByLogin(login);

        JSONArray response = new JSONArray();

        for (var survey: userService.GetUncompletedSurveys(user.getId())) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", survey.getId());
            surveyObject.put("title", survey.getTitle());
            surveyObject.put("description", survey.getDescription());

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/surveys/my")
    public ResponseEntity<?> GetAllSurveys(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!VerifySession(login, sessionId)) return new ResponseEntity<>(false, HttpStatus.OK);
        UserModel_Pevneva user = userService.GetUserByLogin(login);

        if (user.getRole() != UserRole_Pevneva.Admin.value) return new ResponseEntity<>(false, HttpStatus.OK);

        JSONArray response = new JSONArray();

        for (var survey: userService.GetAllSurveys(user.getId())) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", survey.getId());
            surveyObject.put("title", survey.getTitle());
            surveyObject.put("description", survey.getDescription());

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/session/verify")
    public ResponseEntity<?> VerifySession(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!VerifySession(login, sessionId)) return new ResponseEntity<>(false, HttpStatus.OK);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/session/remove")
    public ResponseEntity<?> RemoveSession(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!VerifySession(login, sessionId)) return new ResponseEntity<>(false, HttpStatus.OK);;
        UserModel_Pevneva user = userService.GetUserByLogin(login);

        userService.UpdateSessionIdHash(user.getId(), null);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String password = String.valueOf(json.getString("password").hashCode());

        UserModel_Pevneva user = userService.GetUserByLogin(login);
        if (!user.getPasswordHash().equals(password)) return new ResponseEntity<>(false, HttpStatus.OK);;

        String sessionId = String.valueOf(ThreadLocalRandom.current().nextInt());
        userService.UpdateSessionIdHash(user.getId(), String.valueOf(sessionId.hashCode()));

        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }

    Boolean VerifySession(String login, String sessionId) {
        if (!userService.UserExist(login)) return false;
        if (!Objects.equals(userService.GetUserByLogin(login).getSessionIdHash(), String.valueOf(sessionId.hashCode()))) return false;
        return true;
    }
}
