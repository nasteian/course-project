package com.bsuir.websurvey.services;

import com.bsuir.websurvey.models.User_Pevneva;

public interface UserService_Pevneva {
    Boolean SaveUser(User_Pevneva user);
    Boolean UserExist(String login);
    User_Pevneva GetUserByLogin(String login);

}
