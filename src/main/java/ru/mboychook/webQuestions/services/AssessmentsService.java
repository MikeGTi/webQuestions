package ru.mboychook.webQuestions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mboychook.webQuestions.models.Assessment;
import ru.mboychook.webQuestions.repositories.AssessmentsRepository;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AssessmentsService {

    private final AssessmentsRepository assessmentsRepository;

    @Autowired
    public AssessmentsService(AssessmentsRepository assessmentsRepository) {
        this.assessmentsRepository = assessmentsRepository;
    }

    public List<Assessment> findAll(){
        return assessmentsRepository.findAllByOrderByTitle();
    }

    public List<Assessment> findByTitle(String keyword){
        return assessmentsRepository.findByTitleContainingIgnoreCase(keyword);
    }


    public Assessment findOne(UUID id){
        return assessmentsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Assessment assessment){
        assessment.setCreatedAt(new Date());
        assessmentsRepository.save(assessment);
    }

    @Transactional
    public void update(UUID id, Assessment updatedAssessment){
        updatedAssessment.setId(id);
        assessmentsRepository.save(updatedAssessment);
    }

    @Transactional
    public void delete(UUID id){
        assessmentsRepository.deleteById(id);
    }

}
