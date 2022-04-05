package com.websurvey.websurvey_pevneva.repository_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.User_Pevneva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IUserRepository_Pevneva extends JpaRepository<User_Pevneva, Integer> {
    User_Pevneva findByLogin(String login);
    Boolean existsByLogin(String login);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User_Pevneva u set u.sessionIdHash = :session_id where u.id = :id")
    void updateSessionIdHashById(@Param("session_id") String sessionIdHash, @Param("id") int id);
}
