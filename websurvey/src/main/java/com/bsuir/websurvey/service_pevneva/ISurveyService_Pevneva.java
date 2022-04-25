package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.SurveyModel_Pevneva;

import java.util.List;

public interface ISurveyService_Pevneva {
    void SaveSurvey(SurveyModel_Pevneva survey);
    SurveyModel_Pevneva GetSurvey(int id);
    List<QuestionModel_Pevneva> GetAllQuestions(int id);
    void Delete(int id);
}
