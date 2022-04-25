package com.websurvey.websurvey_pevneva.controller_pevneva;

import com.websurvey.websurvey_pevneva.apis_pevneva.SurveyApi_Pevneva;
import com.websurvey.websurvey_pevneva.apis_pevneva.UserApi_Pevneva;
import com.websurvey.websurvey_pevneva.enums_pevneva.UserRole_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.ISurveyService_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.IUserService_Pevneva;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey")
@CrossOrigin
public class SurveyController_Pevneva {
    @Autowired
    private SurveyApi_Pevneva surveyApi;

    @Autowired
    private UserApi_Pevneva userApi;

    @PostMapping("/{id}/questions")
    public ResponseEntity<?> GetAllQuestions(@PathVariable("id") int id) {
        JSONArray response = new JSONArray();

        for (var question : surveyApi.GetAllQuestions(id)) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", question.getId());
            surveyObject.put("type", question.getType());
            surveyObject.put("wording", question.getWording());

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        UserModel_Pevneva user = userApi.GetUser(json, UserRole_Pevneva.Admin);
        if (user == null) return new ResponseEntity<>(false, HttpStatus.OK);

        SurveyModel_Pevneva survey = new SurveyModel_Pevneva();
        survey.setTitle(json.getString("title"));
        survey.setDescription(json.getString("description"));
        survey.setOwner(user);
        surveyApi.SaveSurvey(survey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/change")
    public ResponseEntity<?> Change(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Pevneva survey = surveyApi.GetSurvey(json, id);
        if (survey == null) return new ResponseEntity<>(false, HttpStatus.OK);

        survey.setTitle(json.getString("title"));
        survey.setDescription(json.getString("description"));
        surveyApi.SaveSurvey(survey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> Delete(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Pevneva survey = surveyApi.GetSurvey(json, id);
        if (survey == null) return new ResponseEntity<>(false, HttpStatus.OK);

        surveyApi.Delete(survey.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
