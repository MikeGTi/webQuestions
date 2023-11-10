package ru.mboychook.webQuestions.util;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mboychook.webQuestions.models.Answer;
import ru.mboychook.webQuestions.models.DifficultyEnum;
import ru.mboychook.webQuestions.models.Question;
import ru.mboychook.webQuestions.services.QuestionsService;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class QuestionValidator implements Validator {
    private final QuestionsService questionsService;

    @Autowired
    public QuestionValidator(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Question.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Question question = (Question) target;

        //answers
        List<Answer> answers = question.getAnswers();
        if (answers.size() == 0){
            errors.rejectValue("answers", "Que_001","Question must have one answer at least");
        } else {
            final List<Answer> correctAnswers = answers.stream()
                                                       .filter(answer -> answer.getCorrectAnswer() == Boolean.FALSE)
                                                       .toList();
            if (correctAnswers.size() == 0) {
                errors.rejectValue("answers", "Que_002", "Question must have one answer, with correct answer 'true', at least");
            }
        }


        /*if ((questionsService.findOne(question.getId())) != null){
            errors.rejectValue("id","", "This id already taken");
        }*/

        //difficulty
        if(!DifficultyEnum.isMember(question.getDifficulty().toString())){
            errors.rejectValue("difficulty","Que_003", "Question difficulty non correct");
        }

    }
}
