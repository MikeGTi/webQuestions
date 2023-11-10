package ru.mboychook.webQuestions.models;

import jakarta.validation.Valid;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


@Entity
@Table(name="question")
@DynamicUpdate
@DynamicInsert
//@SelectBeforeUpdate
public class Question implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Question.class);

    @Serial
    private static final long serialVersionUID = -5925620994711686560L;

    public static final String ENTRIES_DELIMITER = ";";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

    @Column(name = "assessment_id")
    private UUID assessment_id;

    @NotEmpty(message = "Stem should Not be empty")
    @Size(min = 3, max = 4096, message = "Stem should be between 3 and 4096 characters")
    @Column(name = "stem")
    private String stem;

    @NotEmpty(message = "Title should Not be empty")
    @Size(min = 3, max = 1024, message = "Title should be between 3 and 1024 characters")
    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private DifficultyEnum difficulty;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    @Column(name = "created_at")
    private Date createdAt;


    @OneToMany(fetch = FetchType.EAGER,
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               mappedBy = "question")
    @Valid
    private List<Answer> answers = new ArrayList<>(); //new HashSet<>();

    @ManyToMany(mappedBy = "questions")
    private List<Assessment> assessments = new ArrayList<>();

    public Question() {
        this.addAnswer(new Answer());
        //this.createdAt = new Date();
    }

    public Question(UUID id) {
        this.id = id;
    }

    public Question(String title, String stem, List<Answer> answers) {
        this.title = title;
        this.stem = stem;
        this.answers = answers;
    }

    public Question(String title, String stem, String answers) {
        this.title = title;
        this.stem = stem;
        this.setAnswersByString(answers);
    }

    private static Iterator<String> answerChar = new Alphabet('a','z').iterator();
    //private static Iterator<String> answerChar = new Alphabet('а','я').iterator(); //for russian chars usage

    private static String getAnswerChar() {
        return String.valueOf(answerChar.next());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(UUID assessment_id) {
        this.assessment_id = assessment_id;
    }

    public DifficultyEnum getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyEnum difficulty) {
        this.difficulty = difficulty;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public static void setAnswerChar(Iterator<String> answerChar) {
        Question.answerChar = answerChar;
    }

    public String getAnswersStr() {
        return this.answers
                .stream()
                .map(answer -> getAnswerChar() + ". " + answer.toString())
                .collect(Collectors.joining(ENTRIES_DELIMITER));
    }

    public void setAnswersByString(String answers) {
        if (answers.endsWith(ENTRIES_DELIMITER)){
            answers = answers.substring(0, answers.length() - 1);
        }

        Arrays.stream(answers.split(ENTRIES_DELIMITER))
              .forEach(answer -> this.answers.add(new Answer(answer, answer.endsWith("true") ? TRUE : FALSE)));
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public void addAnswer(Answer answer){
        answer.setQuestion(this);
        this.answers.add(answer);
    }

    public void removeAnswer(int index){
        this.answers.get(index).setQuestion(null);
        this.answers.remove(index);
    }

    public void removeAnswer(Answer answer){
        answer.setQuestion(null);
        this.answers.remove(answer);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = new HashSet<>(answers).stream().toList();
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    // for questions - assessments many-to-many relations
    public void addAssessment(Assessment assessment){
        this.assessments.add(assessment);
        //assessment.getQuestions().add(this);
    }

    public void removeAssessment(UUID assessmentID){
        Assessment assessment = this.assessments.stream()
                .filter(t -> t.getId() == assessmentID)
                .findFirst()
                .orElse(null);
        if (assessment != null) {
            this.assessments.remove(assessment);
            //assessment.getQuestions().remove(this);
        }
    }

    public void removeAssessment(int assessmentListIndex){
        this.assessments.remove(assessmentListIndex);
        //assessment.getQuestions().remove(this);
    }

}
