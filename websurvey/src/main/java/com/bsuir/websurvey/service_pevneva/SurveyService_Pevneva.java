package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.UserModel_Pevneva;
import com.websurvey.websurvey_pevneva.repository_pevneva.ISurveyRepository_Pevneva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService_Pevneva implements ISurveyService_Pevneva {
    @Autowired
    private ISurveyRepository_Pevneva surveyRepository;

    @Override
    public void SaveSurvey(SurveyModel_Pevneva survey) { surveyRepository.save(survey); }

    @Override
    public SurveyModel_Pevneva GetSurvey(int id) { return surveyRepository.findById(id); }

    @Override
    public List<QuestionModel_Pevneva> GetAllQuestions(int id) { return surveyRepository.GetAllQuestions(id); }

    @Override
    public void Delete(int id) { surveyRepository.deleteById(id); }
}
