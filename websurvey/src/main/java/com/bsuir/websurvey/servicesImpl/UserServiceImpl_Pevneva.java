package com.bsuir.websurvey.servicesImpl;

import com.bsuir.websurvey.models.User_Pevneva;
import com.bsuir.websurvey.repositories.UserRepository_Pevneva;
import com.bsuir.websurvey.services.UserService_Pevneva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl_Pevneva implements UserService_Pevneva {
    @Autowired
    private UserRepository_Pevneva userRepository;

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
}
