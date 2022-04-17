package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import com.websurvey.websurvey_pevneva.repository_pevneva.IQuestionRepository_Pevneva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService_Pevneva implements IQuestionService_Pevneva{
    @Autowired
    private IQuestionRepository_Pevneva questionRepository;

    @Override
    public List<AnswerModel_Pevneva> GetAllAnswers(int id) { return questionRepository.GetAllAnswers(id); }
}
