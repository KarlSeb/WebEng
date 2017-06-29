package qasystem.persistence.entities;

import javax.persistence.*;

/**
 * Kapselt die Daten einer Antwort für die Java Persistence API
 */
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question.id")
    private Question parentQuestion;

    private String text;

    protected Answer(){}

    public Answer(String text) {
        this.parentQuestion = parentQuestion;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format(
                "Answer[id=%d, question='%s', text='%s']",
                id, parentQuestion.toString(), text);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Question getParentQuestion() {
        return parentQuestion;
    }

    public void setParentQuestion(Question parentQuestion) {
        this.parentQuestion = parentQuestion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
