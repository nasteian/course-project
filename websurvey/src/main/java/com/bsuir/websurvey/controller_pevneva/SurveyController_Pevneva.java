package com.websurvey.websurvey_pevneva.controller_pevneva;

import com.fasterxml.jackson.databind.JsonNode;
import com.websurvey.websurvey_pevneva.apis_pevneva.AnswerApi_Pevneva;
import com.websurvey.websurvey_pevneva.apis_pevneva.QuestionApi_Pevneva;
import com.websurvey.websurvey_pevneva.apis_pevneva.SurveyApi_Pevneva;
import com.websurvey.websurvey_pevneva.apis_pevneva.UserApi_Pevneva;
import com.websurvey.websurvey_pevneva.enums_pevneva.QuestionType_Pevneva;
import com.websurvey.websurvey_pevneva.enums_pevneva.UserRole_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.ISurveyService_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.IUserService_Pevneva;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;

@RestController
@RequestMapping("/survey")
@CrossOrigin
public class SurveyController_Pevneva {
    @Autowired
    private SurveyApi_Pevneva surveyApi;

    @Autowired
    private UserApi_Pevneva userApi;

    @Autowired
    private AnswerApi_Pevneva answerApi;

    @Autowired
    private QuestionApi_Pevneva questionApi;

    @GetMapping("/{id}")
    public ResponseEntity<?> GetSurvey(@PathVariable("id") int id) {
        SurveyModel_Pevneva survey = surveyApi.GetSurvey(id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        JSONObject response = new JSONObject();
        response.put("title", survey.getTitle());
        response.put("description", survey.getDescription());

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<?> GetAllQuestions(@PathVariable("id") int id) {
        JSONArray response = new JSONArray();

        for (var question : surveyApi.GetAllQuestions(id)) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", question.getId());
            surveyObject.put("type", question.getType());
            surveyObject.put("wording", question.getWording());
            if (question.getType() == QuestionType_Pevneva.VARIANT.id) surveyObject.put("variants", new JSONArray(question.getVariants()));

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/{id}/questions_with_answers")
    public ResponseEntity<?> GetQuestionsWithAnswers(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Pevneva survey = surveyApi.GetSurvey(id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        UserModel_Pevneva user = userApi.GetUser(json);
        if (user == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (survey.getOwner() != user) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        JSONArray response = new JSONArray();

        for (var question : surveyApi.GetAllQuestions(id)) {
            JSONObject surveyObject = new JSONObject();
            surveyObject.put("id", question.getId());
            surveyObject.put("type", question.getType());
            surveyObject.put("wording", question.getWording());
            surveyObject.put("answer", question.getAnswer());

            JSONArray answers = new JSONArray();
            for (var answer : questionApi.GetAllAnswers(question.getId())) {
                JSONObject answerJson = new JSONObject();
                answerJson.put("answer", answer.getAnswer());
                answerJson.put("owner", answer.getOwner().getLogin());
                answers.put(answerJson);
            }
            surveyObject.put("answers", answers);

            if (question.getType() == QuestionType_Pevneva.VARIANT.id) surveyObject.put("variants", new JSONArray(question.getVariants()));

            response.put(surveyObject);
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody String request) {
        JSONObject json = new JSONObject(request);

        UserModel_Pevneva user = userApi.GetUser(json, UserRole_Pevneva.Admin);
        if (user == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        SurveyModel_Pevneva survey = new SurveyModel_Pevneva();
        survey.setTitle(json.getString("title"));
        survey.setDescription(json.getString("description"));
        survey.setOwner(user);
        surveyApi.SaveSurvey(survey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/modify_all_survey")
    public ResponseEntity<?> ModifyAllSurvey(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Pevneva survey = surveyApi.GetSurvey(json, id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        UserModel_Pevneva user = userApi.GetUser(json);
        if (survey.getOwner() != user) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        for (var question : surveyApi.GetAllQuestions(survey.getId())) {
            questionApi.Delete(question.getId());
        }

        for (var item : json.getJSONArray("questions")) {
            JSONObject questionJson = (JSONObject)item;
            QuestionModel_Pevneva question = new QuestionModel_Pevneva();
            question.setWording(questionJson.getString("wording"));
            question.setType(questionJson.getInt("type"));
            if (question.getType() == QuestionType_Pevneva.VARIANT.id) question.setVariants(questionJson.getJSONArray("variants").toString());
            question.setAnswer(questionJson.getString("answer"));
            question.setSurvey(survey);

            questionApi.SaveQuestion(question);
        }

        survey.setTitle(json.getString("title"));
        survey.setDescription(json.getString("description"));
        surveyApi.SaveSurvey(survey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/update_answers")
    public ResponseEntity<?> UpdateAnswers(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Pevneva survey = surveyApi.GetSurvey(id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        UserModel_Pevneva user = userApi.GetUser(json);
        if (user == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        if (user.getBanned()) return new ResponseEntity<>(HttpStatus.LOCKED);

        for (var item : json.getJSONArray("answers")) {
            JSONObject answerJson = (JSONObject)item;
            AnswerModel_Pevneva answer = new AnswerModel_Pevneva();
            answer.setAnswer(answerJson.getString("answer"));
            answer.setQuestion(questionApi.GetQuestion(answerJson.getInt("id")));
            answer.setOwner(user);
            answerApi.SaveAnswer(answer);
        }

        Set<SurveyModel_Pevneva> completedSurveys = user.getCompletedSurveys();
        if (!completedSurveys.contains(survey)) {
            completedSurveys.add(survey);
            user.setCompletedSurveys(completedSurveys);
            userApi.SaveUser(user);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> Delete(@PathVariable("id") int id, @RequestBody String request) {
        JSONObject json = new JSONObject(request);

        SurveyModel_Pevneva survey = surveyApi.GetSurvey(json, id);
        if (survey == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        surveyApi.Delete(survey.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
