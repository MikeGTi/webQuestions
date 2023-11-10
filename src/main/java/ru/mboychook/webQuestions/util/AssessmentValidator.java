package ru.mboychook.webQuestions.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mboychook.webQuestions.models.Assessment;
import ru.mboychook.webQuestions.services.AssessmentsService;


@Component
public class AssessmentValidator implements Validator {

    private final AssessmentsService assessmentsService;

    public AssessmentValidator(AssessmentsService assessmentsService) {
        this.assessmentsService = assessmentsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Assessment.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Assessment assessment = (Assessment) target;
        if (assessmentsService.findOne(assessment.getId()) != null){
            errors.rejectValue("id","", "This assessment id already taken");
        }
    }
}
