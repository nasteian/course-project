package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;

import java.util.List;

public interface IUserService_Pevneva {
    Boolean SaveUser(UserModel_Pevneva user);
    Boolean UserExist(String login);
    UserModel_Pevneva GetUserByLogin(String login);
    void UpdateSessionIdHash(int id, String sessionIdHash);
    List<SurveyModel_Pevneva> GetAllSurveys(int id);
    List<SurveyModel_Pevneva> GetUncompletedSurveys(int id);
}
