package com.websurvey.websurvey_pevneva.service_pevneva;

import com.websurvey.websurvey_pevneva.model_pevneva.AnswerModel_Pevneva;
import com.websurvey.websurvey_pevneva.model_pevneva.QuestionModel_Pevneva;
import com.websurvey.websurvey_pevneva.repository_pevneva.IQuestionRepository_Pevneva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService_Pevneva implements IQuestionService_Pevneva{
    @Autowired
    private IQuestionRepository_Pevneva questionRepository;

    @Override
    public void SaveQuestion(QuestionModel_Pevneva question) { questionRepository.save(question); }

    @Override
    public List<AnswerModel_Pevneva> GetAllAnswers(int id) { return questionRepository.GetAllAnswers(id); }

    @Override
    public QuestionModel_Pevneva GetQuestion(int id) { return questionRepository.findById(id); }

    @Override
    public void Delete(int id) { questionRepository.deleteById(id); }
}
