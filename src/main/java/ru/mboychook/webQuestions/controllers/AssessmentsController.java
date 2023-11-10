package ru.mboychook.webQuestions.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mboychook.webQuestions.models.Question;
import ru.mboychook.webQuestions.models.Question;
import ru.mboychook.webQuestions.services.AssessmentsService;
import ru.mboychook.webQuestions.util.AssessmentValidator;
import ru.mboychook.webQuestions.models.Assessment;

import java.util.UUID;

@Controller
@RequestMapping("/assessments")
public class AssessmentsController {

    private final AssessmentsService assessmentsService;
    private final AssessmentValidator assessmentValidator;

    @Autowired
    public AssessmentsController(AssessmentsService assessmentsService, AssessmentValidator assessmentValidator) {
        this.assessmentsService = assessmentsService;
        this.assessmentValidator = assessmentValidator;
    }

    @GetMapping()
    public String allAssessments(Model model){
        model.addAttribute("assessments", assessmentsService.findAll());
        return "/assessments/index";
    }

    @GetMapping("/{id}")
    public String showAssessment(@PathVariable("id") String id, Model model){
        model.addAttribute("assessment", assessmentsService.findOne(UUID.fromString(id)));
        return "/assessments/show";
    }

    @GetMapping("/new")
    public String newAssessment(@ModelAttribute("assessment") Assessment assessment){
        return "/assessments/new";
    }

    @PostMapping()
    public String createAssessment(@ModelAttribute("assessment") @Valid Assessment assessment,
                                                              BindingResult bindingResult){
        assessmentValidator.validate(assessment, bindingResult);
        if (bindingResult.hasErrors()){ return "/assessments/new"; }
        assessmentsService.save(assessment);
        return "redirect:/assessments";
    }

    @GetMapping("/{id}/edit")
    public String editAssessment(Model model, @PathVariable("id") String id){
        model.addAttribute("assessment", assessmentsService.findOne(UUID.fromString(id)));
        return "/assessments/edit";
    }

    @PatchMapping("/{id}")
    public String updateAssessment(@ModelAttribute("assessment") @Valid Assessment assessment,
                                   BindingResult bindingResult,
                                   @PathVariable("id") String id){

        if (bindingResult.hasErrors()){ return "assessments/edit"; }
        assessmentsService.update(UUID.fromString(id), assessment);
        return "redirect:/assessments";
    }

    @DeleteMapping("/{id}")
    public String deleteAssessment(@PathVariable("id") String id){
        assessmentsService.delete(UUID.fromString(id));
        return "redirect:/assessments";
    }

    // assessments field section
    @PatchMapping(value = "/{assessment_id}/edit", params={"addQuestion"})
    public String addQuestion(@ModelAttribute("assessment") Assessment assessment,
                              @PathVariable("assessment_id") String assessment_id,
                              @RequestParam("addQuestion") Boolean addQuestion) {
        if (addQuestion){
            Question question = new Question();
            question.getAssessments().add(assessment);
            assessment.getQuestions().add(question);
        }
        return "/assessments/edit";
    }

    @PatchMapping(value = "/{assessment_id}/edit", params={"removeQuestion"})
    public String removeQuestion(@ModelAttribute("assessment") Assessment assessment,
                                 @PathVariable("assessment_id") String assessment_id,
                                 @RequestParam("removeQuestion") String questionsListIndex) {
        Question question =  assessment.getQuestions().get(Integer.valueOf(questionsListIndex).intValue());
        question.getAssessments().remove(assessment);
        assessment.getQuestions().remove(Integer.valueOf(questionsListIndex).intValue());

        return "/assessments/edit";
    }
    
}
