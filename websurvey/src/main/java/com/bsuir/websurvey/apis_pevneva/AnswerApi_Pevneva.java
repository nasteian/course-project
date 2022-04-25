package com.websurvey.websurvey_pevneva.apis_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.IAnswerService_Pevneva;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class AnswerApi_Pevneva {
    @Autowired
    private IAnswerService_Pevneva answerService;

    @Autowired
    private UserApi_Pevneva userApi;

    public void SaveAnswer(AnswerModel_Pevneva answer) { answerService.SaveSurvey(answer); }
    public AnswerModel_Pevneva GetAnswer(int id) { return answerService.GetAnswer(id); }
    public void Delete(int id) { answerService.Delete(id); }

    public AnswerModel_Pevneva GetAnswer(JSONObject json, int id) {
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!userApi.VerifySession(login, sessionId)) return null;
        UserModel_Pevneva user = userApi.GetUserByLogin(login);

        AnswerModel_Pevneva answer = GetAnswer(id);

        if (answer.getOwner().getId() != user.getId()) return null;
        return answer;
    }
}
