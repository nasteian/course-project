package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.User_Pevneva;

public interface IUserService_Pevneva {
    Boolean SaveUser(User_Pevneva user);
    Boolean UserExist(String login);
    User_Pevneva GetUserByLogin(String login);
    void UpdateSessionIdHashById(int id, String sessionIdHash);
}
