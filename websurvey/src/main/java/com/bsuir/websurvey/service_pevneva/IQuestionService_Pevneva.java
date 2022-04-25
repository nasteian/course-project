package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;

import java.util.List;

public interface IQuestionService_Pevneva {
    void SaveQuestion(QuestionModel_Pevneva question);
    List<AnswerModel_Pevneva> GetAllAnswers(int id);
    QuestionModel_Pevneva GetQuestion(int id);
    void Delete(int id);
}
