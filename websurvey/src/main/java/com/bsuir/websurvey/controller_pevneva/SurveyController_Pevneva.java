package com.websurvey.websurvey_pevneva.controller_pevneva;

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
    private ISurveyService_Pevneva surveyService;

    @PostMapping("/{id}/questions")
    public ResponseEntity<?> GetAllQuestions(@PathVariable("id") int id) {
        JSONArray response = new JSONArray();

        for (var question: surveyService.GetAllQuestions(id)) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", question.getId());
            surveyObject.put("type", question.getType());
            surveyObject.put("wording", question.getWording());

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }
}
