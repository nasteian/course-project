package com.bsuir.websurvey.repositories;

import com.bsuir.websurvey.models.User_Pevneva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository_Pevneva extends JpaRepository<User_Pevneva, Integer> {
    User_Pevneva findByLogin(String login);
    Boolean existsByLogin(String login);
}