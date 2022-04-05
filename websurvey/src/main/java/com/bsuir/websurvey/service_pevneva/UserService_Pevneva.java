package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.User_Pevneva;
import com.websurvey.websurvey_pevneva.repository_pevneva.IUserRepository_Pevneva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService_Pevneva implements IUserService_Pevneva {
    @Autowired
    private IUserRepository_Pevneva userRepository;

    @Override
    public Boolean SaveUser(User_Pevneva user) {
        if (UserExist(user.getLogin())) return false;
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean UserExist(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public User_Pevneva GetUserByLogin(String login) { return userRepository.findByLogin(login); }

    @Override
    public void UpdateSessionIdHashById(int id, String sessionIdHash) { userRepository.updateSessionIdHashById(sessionIdHash, id); }
}
