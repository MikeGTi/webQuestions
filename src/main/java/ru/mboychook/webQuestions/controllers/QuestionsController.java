package ru.mboychook.webQuestions.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mboychook.webQuestions.models.Answer;
import ru.mboychook.webQuestions.models.Question;
import ru.mboychook.webQuestions.services.QuestionsService;
import ru.mboychook.webQuestions.util.QuestionValidator;


import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    private final QuestionsService questionsService;

    private final QuestionValidator questionValidator;

    @Autowired
    public QuestionsController(QuestionsService questionsService, QuestionValidator questionValidator) {
        this.questionsService = questionsService;
        this.questionValidator = questionValidator;
    }

    @GetMapping()
    public String allQuestions(Model model){
        model.addAttribute("questions", questionsService.findAll());
        return "/questions/index";
    }

    @GetMapping("/{question_id}")
    public String showQuestion(@PathVariable("question_id") String id,
                               Model model){
        model.addAttribute("question", questionsService.findOne(UUID.fromString(id)));
        return "/questions/show";
    }

    @GetMapping("/new")
    public String newQuestion(@ModelAttribute("question") Question question){
        return "/questions/new";
    }

    @PostMapping()
    public String createQuestion(@ModelAttribute("question") @Valid Question question,
                                 BindingResult bindingResult){

        questionValidator.validate(question, bindingResult);
        if (bindingResult.hasErrors()){ return "/questions/new"; }

        List<Answer> answers = question.getAnswers();
                     answers.forEach(answer -> answer.setQuestion(question));
        questionsService.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{question_id}/edit")
    public String editQuestion(@NotNull Model model,
                               @PathVariable("question_id") String question_id){
        model.addAttribute("question", questionsService.findOne(UUID.fromString(question_id)));
        return "/questions/edit";
    }

    @PatchMapping("/{question_id}/edit")
    public String updateQuestion(@ModelAttribute("question") @Valid Question question,
                                 BindingResult bindingResult,
                                 @PathVariable("question_id") String question_id){

        questionValidator.validate(question, bindingResult);
        if (bindingResult.hasErrors()){ return "/questions/edit"; }

        questionsService.update(UUID.fromString(question_id), question);
        return "redirect:/questions/{question_id}";
    }

    @DeleteMapping("/{question_id}")
    public String deleteQuestion(@PathVariable("question_id") String question_id){
        questionsService.delete(UUID.fromString(question_id));
        return "redirect:/questions";
    }

    // question answers field section
    @PatchMapping(value = "/{question_id}/edit", params={"addAnswer"})
    public String addAnswer(@ModelAttribute("question") Question question,
                            @PathVariable("question_id") String question_id,
                            @RequestParam("addAnswer") Boolean addAnswer) {
        if (addAnswer){
            Answer answer = new Answer();
            answer.setQuestion(question);
            question.addAnswer(answer);
        }
        return "/questions/edit";
    }

    @PatchMapping(value = "/{question_id}/edit", params={"removeAnswer"})
    public String removeAnswer(@ModelAttribute("question") Question question,
                               @PathVariable("question_id") String question_id,
                               @RequestParam("removeAnswer") String answersListIndex) {

        question.removeAnswer(Integer.parseInt(answersListIndex));
        return "/questions/edit";
    }

}
