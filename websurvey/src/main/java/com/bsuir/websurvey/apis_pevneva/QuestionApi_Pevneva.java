package com.websurvey.websurvey_pevneva.apis_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.IQuestionService_Pevneva;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configurable
public class QuestionApi_Pevneva {
    @Autowired
    private IQuestionService_Pevneva questionService;

    @Autowired
    private SurveyApi_Pevneva surveyApi;

    @Autowired
    private UserApi_Pevneva userApi;

    public List<AnswerModel_Pevneva> GetAllAnswers(int id) { return questionService.GetAllAnswers(id); }

    public void SaveQuestion(QuestionModel_Pevneva question) { questionService.SaveQuestion(question); }

    public QuestionModel_Pevneva GetQuestion(int id) { return questionService.GetQuestion(id); }

    public void Delete(int id) { questionService.Delete(id); }

    public QuestionModel_Pevneva GetQuestion(JSONObject json, int id) {
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!userApi.VerifySession(login, sessionId)) return null;
        UserModel_Pevneva user = userApi.GetUserByLogin(login);

        QuestionModel_Pevneva question = GetQuestion(id);

        if (question.getSurvey().getOwner().getId() != user.getId()) return null;
        return question;
    }
}
