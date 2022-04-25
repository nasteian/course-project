package com.websurvey.websurvey_pevneva.apis_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.ISurveyService_Pevneva;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configurable
public class SurveyApi_Pevneva {
    @Autowired
    private ISurveyService_Pevneva surveyService;

    @Autowired
    private UserApi_Pevneva userApi;

    public void SaveSurvey(SurveyModel_Pevneva survey) { surveyService.SaveSurvey(survey); }

    public List<QuestionModel_Pevneva> GetAllQuestions(int id) { return surveyService.GetAllQuestions(id); }

    public SurveyModel_Pevneva GetSurvey(int id) { return surveyService.GetSurvey(id); }

    public void Delete(int id) { surveyService.Delete(id); }

    public SurveyModel_Pevneva GetSurvey(JSONObject json, int id) {
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!userApi.VerifySession(login, sessionId)) return null;
        UserModel_Pevneva user = userApi.GetUserByLogin(login);

        SurveyModel_Pevneva survey = GetSurvey(id);

        if (survey.getOwner().getId() != user.getId()) return null;
        return survey;
    }
}
