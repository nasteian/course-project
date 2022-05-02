package com.websurvey.websurvey_pevneva.controller_pevneva;

import com.websurvey.websurvey_pevneva.apis_pevneva.AnswerApi_Pevneva;
import com.websurvey.websurvey_pevneva.apis_pevneva.QuestionApi_Pevneva;
import com.websurvey.websurvey_pevneva.apis_pevneva.SurveyApi_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
@CrossOrigin
public class AnswerController_Pevneva {
    @Autowired
    private AnswerApi_Pevneva answerApi;

    @Autowired
    private QuestionApi_Pevneva questionApi;

    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        QuestionModel_Pevneva question = questionApi.GetQuestion(json, json.getInt("question"));
        if (question == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        AnswerModel_Pevneva answer = new AnswerModel_Pevneva();
        answer.setAnswer(json.getString("answer"));
        answer.setQuestion(question);
        answer.setOwner(question.getSurvey().getOwner());

        answerApi.SaveAnswer(answer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/change")
    public ResponseEntity<?> Change(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        AnswerModel_Pevneva answer = answerApi.GetAnswer(json, id);
        if (answer == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        answer.setAnswer(json.getString("answer"));

        answerApi.SaveAnswer(answer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> Delete(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        AnswerModel_Pevneva answer = answerApi.GetAnswer(json, id);
        if (answer == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        answerApi.Delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
