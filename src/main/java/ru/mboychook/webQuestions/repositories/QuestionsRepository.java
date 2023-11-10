package ru.mboychook.webQuestions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mboychook.webQuestions.models.Question;


import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, UUID> {
    Question findDistinctByTitle(String title);

    Question findDistinctByStem(String stem);

    List<Question> findAllByTitle(String title);

    List<Question> findAllByOrderByTitle();

    List<Question> findAllByOrderByCreatedAt();

    //List<Question> findQuestionsByAssessment_id(UUID assessmentId);

}
