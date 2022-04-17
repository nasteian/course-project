package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;

import java.util.List;

public interface ISurveyService_Pevneva {
    List<QuestionModel_Pevneva> GetAllQuestions(int id);
}
