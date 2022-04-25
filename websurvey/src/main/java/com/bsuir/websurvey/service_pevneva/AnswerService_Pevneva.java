package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import com.websurvey.websurvey_pevneva.repository_pevneva.IAnswerRepository_Pevneva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService_Pevneva implements IAnswerService_Pevneva {
    @Autowired
    private IAnswerRepository_Pevneva answerRepository;

    @Override
    public void SaveSurvey(AnswerModel_Pevneva answer) { answerRepository.save(answer); }

    @Override
    public AnswerModel_Pevneva GetAnswer(int id) { return answerRepository.findById(id); }

    @Override
    public void Delete(int id) { answerRepository.deleteById(id); }
}
