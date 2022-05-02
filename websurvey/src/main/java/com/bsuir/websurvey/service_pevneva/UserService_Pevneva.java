package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import com.websurvey.websurvey_pevneva.repository_pevneva.IUserRepository_Pevneva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService_Pevneva implements IUserService_Pevneva {
    @Autowired
    private IUserRepository_Pevneva userRepository;

    @Override
    public Boolean SaveUser(UserModel_Pevneva user) {
        if (UserExist(user.getLogin())) return false;
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean UserExist(String login) { return userRepository.existsByLogin(login); }

    @Override
    public UserModel_Pevneva GetUserByLogin(String login) { return userRepository.findByLogin(login); }

    @Override
    public void UpdateSessionIdHash(int id, String sessionIdHash) { userRepository.updateSessionIdHashById(sessionIdHash, id); }

    @Override
    public List<SurveyModel_Pevneva> GetAllSurveys(int id) { return userRepository.GetAllSurveys(id); }

    @Override
    public List<SurveyModel_Pevneva> GetUncompletedSurveys(int id) { return userRepository.GetUncompletedSurveys(id); }
}
