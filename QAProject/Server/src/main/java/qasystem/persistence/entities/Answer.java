package qasystem.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Kapselt die Daten einer Antwort für die Java Persistence API
 */
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private Question parentQuestion;
    private String text;

    protected Answer(){}

    public Answer(Question parentQuestion, String text) {
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
