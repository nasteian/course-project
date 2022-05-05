package com.websurvey.websurvey_pevneva.controller_pevneva;

import com.websurvey.websurvey_pevneva.apis_pevneva.QuestionApi_Pevneva;
import com.websurvey.websurvey_pevneva.apis_pevneva.SurveyApi_Pevneva;
import com.websurvey.websurvey_pevneva.enums_pevneva.QuestionType_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController_Pevneva {
    @Autowired
    private QuestionApi_Pevneva questionApi;

    @Autowired
    private SurveyApi_Pevneva surveyApi;

    @PostMapping("/{id}/answers")
    public ResponseEntity<?> GetAllAnswers(@PathVariable("id") int id) {
        JSONArray response = new JSONArray();

        for (var answer: questionApi.GetAllAnswers(id)) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", answer.getId());
            surveyObject.put("answer", answer.getAnswer());
            surveyObject.put("owner_id", answer.getOwner().getId());

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

}
