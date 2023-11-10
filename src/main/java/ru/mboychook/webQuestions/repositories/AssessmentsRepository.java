package ru.mboychook.webQuestions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mboychook.webQuestions.models.Assessment;


import java.util.List;
import java.util.UUID;

@Repository
public interface AssessmentsRepository extends JpaRepository<Assessment, UUID> {

    Assessment findDistinctByTitleContainingIgnoreCase(String title);

    Assessment findDistinctByDescriptionContainingIgnoreCase(String description);

    List<Assessment> findAllByTitle(String title);

    List<Assessment> findAllByOrderByTitle();

    List<Assessment> findAllByOrderByCreatedAt();

    //List<Assessment> findAssessmentsByQuestionId(UUID questionId);


    List<Assessment> findByTitleContainingIgnoreCase(String keyword);
}
