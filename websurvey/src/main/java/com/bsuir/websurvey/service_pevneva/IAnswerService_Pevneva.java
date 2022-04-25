package com.websurvey.websurvey_pevneva.service_pevneva;


import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;

public interface IAnswerService_Pevneva {
    void SaveSurvey(AnswerModel_Pevneva answer);
    AnswerModel_Pevneva GetAnswer(int id);
    void Delete(int id);
}
