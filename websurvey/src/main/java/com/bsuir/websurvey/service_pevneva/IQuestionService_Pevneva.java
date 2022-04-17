package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;

import java.util.List;

public interface IQuestionService_Pevneva {
    List<AnswerModel_Pevneva> GetAllAnswers(int id);
}
