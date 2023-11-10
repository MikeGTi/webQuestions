package ru.mboychook.webQuestions.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="assessment")
//@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Assessment implements Serializable {

    @Serial
    private static final long serialVersionUID = -2708666981943453365L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

    @NotEmpty(message = "Title should Not be empty")
    @Size(min = 3, max = 1024, message = "Title should be between 3 and 1024 characters")
    @Column(name = "title")
    private String title;

    @Size(min = 3, max = 1024, message = "Description should be between 3 and 1024 characters")
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = { CascadeType.ALL })
    @JoinTable(name = "assessment_question",
               joinColumns = { @JoinColumn(name = "assessment_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "question_id", referencedColumnName = "id") })
    private List<Question> questions = new ArrayList<>();

    public Assessment() {
    }

    public Assessment(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Assessment(String title, String description, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // for assessments - questions many-to-many relations
    public void addQuestion(Question question){
        this.questions.add(question);
        //question.getAssessments().add(this);
    }

    public void removeQuestion(UUID questionID){
        Question question = this.questions.stream()
                                          .filter(t -> t.getId() == questionID)
                                          .findFirst()
                                          .orElse(null);
        if (question != null) {
            this.questions.remove(question);
            //question.getAssessments().remove(this);
        }
    }

    public void removeQuestion(int questionListIndex){
        this.questions.remove(questionListIndex);
    }
}
