package qasystem.persistence.entities;

import javax.persistence.*;
import java.util.GregorianCalendar;

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

    private GregorianCalendar date;

    private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user.id")
    private User user;

    protected Answer(){}

    public Answer(Question parentQuestion, String text, User user) {
        this.parentQuestion = parentQuestion;
        this.text = text;
        date = new GregorianCalendar();
        accepted = false;
        this.user = user;
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

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
