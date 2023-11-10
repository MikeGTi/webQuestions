package ru.mboychook.webQuestions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.webQuestions.models.Question;
import ru.mboychook.webQuestions.repositories.QuestionsRepository;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class QuestionsService {

    private final QuestionsRepository questionsRepository;

    @Autowired
    public QuestionsService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public List<Question> findAll() {
        return questionsRepository.findAllByOrderByTitle();
    }

    public Question findOne(UUID id) {
        return questionsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Question question) {
        question.setCreatedAt(new Date());
        questionsRepository.save(question);
    }

    @Transactional
    public void update(UUID id, Question updatedQuestion) {
        updatedQuestion.setId(id);
        //updatedQuestion.getAnswers().stream().forEach(answer -> answer.setId(id));
        questionsRepository.save(updatedQuestion);
    }

    @Transactional
    public void delete(UUID id) {
        questionsRepository.deleteById(id);
    }
}
