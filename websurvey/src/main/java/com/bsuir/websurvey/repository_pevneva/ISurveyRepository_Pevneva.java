package com.websurvey.websurvey_pevneva.repository_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ISurveyRepository_Pevneva extends JpaRepository<SurveyModel_Pevneva, Integer> {
    SurveyModel_Pevneva findById(int id);
    void deleteById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select q from Surveys s inner join Questions q on s.id = q.survey where s.id = :id")
    List<QuestionModel_Pevneva> GetAllQuestions(@Param("id") int id);
}
