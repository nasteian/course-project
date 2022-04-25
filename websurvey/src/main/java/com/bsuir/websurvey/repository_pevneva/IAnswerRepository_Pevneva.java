package com.websurvey.websurvey_pevneva.repository_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerRepository_Pevneva extends JpaRepository<AnswerModel_Pevneva, Integer> {
    AnswerModel_Pevneva findById(int id);
    void deleteById(int id);
}
