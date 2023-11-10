package ru.mboychook.webQuestions.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="answer")
@DynamicUpdate
@DynamicInsert
@SelectBeforeUpdate
public class Answer implements Serializable {
    @Serial
    private static final long serialVersionUID = 6641840673916220075L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID answerId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id",
                referencedColumnName = "id",
                updatable = false)
    private Question question;

    @NotEmpty(message = "Answer text should Not be empty")
    @Size(min = 3, max = 4096, message = "Answer should be between 3 and 4096 characters")
    @Column(name = "stem")
    private String answerStem;

    @NotNull
    @BooleanFlag
    @Column(name = "correct_answer")
    private Boolean correctAnswer;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    @Column(name = "created_at")
    private Date answerCreatedAt;

    public UUID getAnswerId() {
        return answerId;
    }

    public void setAnswerId(UUID id) {
        this.answerId = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswerStem() {
        return answerStem;
    }

    public void setAnswerStem(String text) {
        this.answerStem = text;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correct) {
        correctAnswer = correct;
    }

    public Date getAnswerCreatedAt() {
        return answerCreatedAt;
    }

    public void setAnswerCreatedAt(Date createdAt) {
        this.answerCreatedAt = createdAt;
    }

    public Answer() {
        /*this.answer_stem = "Any text";
        this.correct_answer = Boolean.FALSE;*/
        this.answerCreatedAt = new Date();
    }

    public Answer(String answerStem, Boolean correctAnswer) {
        this.setAnswerStem(answerStem);
        this.correctAnswer = correctAnswer;
    }

    public Answer(UUID answerId, String answerStem, Boolean correctAnswer) {
        this.answerId = answerId;
        this.setAnswerStem(answerStem);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer )) return false;
        return answerId != null && answerId.equals(((Answer) o).getAnswerId());
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return answerStem + ", " + correctAnswer; //(isCorrect ? "true" : "false");
    }
}
