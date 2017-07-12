package qasystem.persistence.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Kapselt die Daten einer Frage für die Java Persistence API
 */
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Lob
    @Column
    private String title;

    @Lob
    @Column
    private String text;

    private GregorianCalendar date;

    private boolean answered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user.id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, targetEntity=Answer.class, mappedBy="parentQuestion")
    private Collection<Answer> answers = new LinkedList<>();

    protected Question(){}

    public Question(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.date = new GregorianCalendar();
        this.answered = false;
        this.user = user;
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

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
