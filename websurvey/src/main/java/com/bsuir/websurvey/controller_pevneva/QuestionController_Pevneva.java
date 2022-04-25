package com.websurvey.websurvey_pevneva.controller_pevneva;

import com.websurvey.websurvey_pevneva.apis_pevneva.QuestionApi_Pevneva;
import com.websurvey.websurvey_pevneva.apis_pevneva.SurveyApi_Pevneva;
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

    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Pevneva survey = surveyApi.GetSurvey(json, json.getInt("survey"));
        if (survey == null) return new ResponseEntity<>(false, HttpStatus.OK);

        QuestionModel_Pevneva question = new QuestionModel_Pevneva();
        question.setWording(json.getString("wording"));
        question.setType(json.getInt("type"));
        question.setAnswer(json.getString("answer"));
        question.setSurvey(survey);

        questionApi.SaveQuestion(question);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/change")
    public ResponseEntity<?> Change(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        QuestionModel_Pevneva question = questionApi.GetQuestion(json, id);
        if (question == null) return new ResponseEntity<>(false, HttpStatus.OK);

        question.setWording(json.getString("wording"));
        question.setType(json.getInt("type"));
        question.setAnswer(json.getString("answer"));

        questionApi.SaveQuestion(question);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> Delete(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        QuestionModel_Pevneva question = questionApi.GetQuestion(json, id);
        if (question == null) return new ResponseEntity<>(false, HttpStatus.OK);

        questionApi.Delete(question.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
