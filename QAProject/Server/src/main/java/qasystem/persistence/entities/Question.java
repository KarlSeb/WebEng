package qasystem.persistence.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Kapselt die Daten einer Frage für die Java Persistence API
 */
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String title;

    private String text;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            targetEntity=Answer.class, mappedBy="parentQuestion")
    private Collection<Answer> answers = new LinkedList<>();

    protected Question(){}

    public Question(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format(
                "Question[id=%d, title='%s', text='%s']",
                id, title, text);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }
}
