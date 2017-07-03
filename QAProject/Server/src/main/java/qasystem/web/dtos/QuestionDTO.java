package qasystem.web.dtos;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Kapselt die Daten für eine Frage.
 */
public class QuestionDTO {
    private long id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String text;

    private GregorianCalendar date;

    private boolean answered;

    @NotNull
    @NotEmpty
    private long userId;

    private Collection<Long> answerIds = new LinkedList<>();


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

    public long getUser() {
        return userId;
    }

    public void setUser(long userId) {
        this.userId = userId;
    }

    public Collection<Long> getAnswers() {
        return answerIds;
    }

    public void setAnswers(Collection<Long> answerIds) {
        this.answerIds = answerIds;
    }
}
