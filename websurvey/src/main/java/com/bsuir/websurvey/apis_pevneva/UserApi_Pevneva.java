package com.websurvey.websurvey_pevneva.apis_pevneva;

import com.websurvey.websurvey_pevneva.enums_pevneva.UserRole_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import com.websurvey.websurvey_pevneva.service_pevneva.IUserService_Pevneva;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Configurable
public class UserApi_Pevneva {
    @Autowired
    private IUserService_Pevneva userService;

    public boolean SaveUser(UserModel_Pevneva model) { return userService.SaveUser(model); }

    public List<UserModel_Pevneva> GetAllUsers() { return userService.GetAllUsers(); }

    public Boolean UserExist(String login) { return userService.UserExist(login); }

    public UserModel_Pevneva GetUserByLogin(String login) { return userService.GetUserByLogin(login); }

    public List<SurveyModel_Pevneva> GetUncompletedSurveys(int id) { return userService.GetUncompletedSurveys(id); }

    public List<SurveyModel_Pevneva> GetMySurveys(int id) { return userService.GetAllSurveys(id); }

    public void UpdateSessionIdHash(int id, String sessionIdHash) { userService.UpdateSessionIdHash(id, sessionIdHash); }

    public UserModel_Pevneva GetUser(JSONObject json) {
        return GetUser(json, UserRole_Pevneva.User);
    }

    public UserModel_Pevneva GetUser(JSONObject json, UserRole_Pevneva permissionLevel) {
        String login = json.getString("login");
        String sessionId = json.getString("session_id");

        if (!VerifySession(login, sessionId)) return null;
        UserModel_Pevneva user = GetUserByLogin(login);

        if (user.getRole() < permissionLevel.value) return null;
        return user;
    }

    public Boolean VerifySession(String login, String sessionId) {
        if (!UserExist(login)) return false;
        if (!Objects.equals(GetUserByLogin(login).getSessionIdHash(), String.valueOf(sessionId.hashCode()))) return false;
        return true;
    }
}
