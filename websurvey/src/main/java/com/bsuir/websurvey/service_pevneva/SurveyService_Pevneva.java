package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.repository_pevneva.ISurveyRepository_Pevneva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService_Pevneva implements ISurveyService_Pevneva {
    @Autowired
    private ISurveyRepository_Pevneva surveyRepository;

    @Override
    public List<QuestionModel_Pevneva> GetAllQuestions(int id) { return surveyRepository.GetAllQuestions(id); }
}
