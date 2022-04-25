package com.websurvey.websurvey_pevneva.controller_pevneva;

import com.websurvey.websurvey_pevneva.apis_pevneva.UserApi_Pevneva;
import com.websurvey.websurvey_pevneva.enums_pevneva.UserRole_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
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
    private UserApi_Pevneva userApi;

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        UserModel_Pevneva user = new UserModel_Pevneva();
        user.setLogin(json.getString("login"));
        user.setRole(json.getInt("role"));
        user.setPasswordHash(String.valueOf(json.getString("password").hashCode()));
        user.setCodePhraseHash(String.valueOf(json.getString("code_phrase").hashCode()));

        if (userApi.UserExist(user.getLogin())) return new ResponseEntity<>(false, HttpStatus.OK);
        userApi.SaveUser(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/exist/{login}")
    public ResponseEntity<?> LoginExist(@PathVariable("login") String login) {
        Boolean response = userApi.UserExist(login);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/surveys/uncompleted")
    public ResponseEntity<?> GetUncompletedSurveys(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        UserModel_Pevneva user = userApi.GetUser(json);
        if (user == null) return new ResponseEntity<>(false, HttpStatus.OK);

        JSONArray response = new JSONArray();   

        for (var survey: userApi.GetUncompletedSurveys(user.getId())) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", survey.getId());
            surveyObject.put("title", survey.getTitle());
            surveyObject.put("description", survey.getDescription());
            surveyObject.put("author", survey.getOwner().getLogin());

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/surveys/my")
    public ResponseEntity<?> GetAllSurveys(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        UserModel_Pevneva user = userApi.GetUser(json, UserRole_Pevneva.Admin);
        if (user == null) return new ResponseEntity<>(false, HttpStatus.OK);

        JSONArray response = new JSONArray();

        for (var survey: userApi.GetMySurveys(user.getId())) {
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

        if (!userApi.VerifySession(login, sessionId)) return new ResponseEntity<>(false, HttpStatus.OK);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/session/remove")
    public ResponseEntity<?> RemoveSession(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!userApi.VerifySession(login, sessionId)) return new ResponseEntity<>(false, HttpStatus.OK);;
        UserModel_Pevneva user = userApi.GetUserByLogin(login);

        userApi.UpdateSessionIdHash(user.getId(), null);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String login = json.getString("login");
        String password = String.valueOf(json.getString("password").hashCode());

        UserModel_Pevneva user = userApi.GetUserByLogin(login);
        if (!user.getPasswordHash().equals(password)) return new ResponseEntity<>(false, HttpStatus.OK);;

        String sessionId = String.valueOf(ThreadLocalRandom.current().nextInt());
        userApi.UpdateSessionIdHash(user.getId(), String.valueOf(sessionId.hashCode()));

        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }
}
