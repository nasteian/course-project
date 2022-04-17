package com.websurvey.websurvey_pevneva.repository_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IUserRepository_Pevneva extends JpaRepository<UserModel_Pevneva, Integer> {
    UserModel_Pevneva findByLogin(String login);
    Boolean existsByLogin(String login);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update UserModel_Pevneva u set u.sessionIdHash = :session_id where u.id = :id")
    void updateSessionIdHashById(@Param("session_id") String sessionIdHash, @Param("id") int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select s from UserModel_Pevneva u inner join SurveyModel_Pevneva s on u.id = s.owner where u.id = :id")
    List<SurveyModel_Pevneva> GetAllSurveys(@Param("id") int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select s from SurveyModel_Pevneva s where s.id not in (select s.id from UserModel_Pevneva u join u.completedSurveys s where u.id = :id)")
    List<SurveyModel_Pevneva> GetUncompletedSurveys(@Param("id") int id);
}
