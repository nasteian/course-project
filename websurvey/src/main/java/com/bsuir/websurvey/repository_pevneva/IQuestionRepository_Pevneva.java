package com.websurvey.websurvey_pevneva.repository_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IQuestionRepository_Pevneva extends JpaRepository<QuestionModel_Pevneva, Integer> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select a from QuestionModel_Pevneva q inner join AnswerModel_Pevneva a on q.id = a.question where q.id = :id")
    List<AnswerModel_Pevneva> GetAllAnswers(@Param("id") int id);
}
