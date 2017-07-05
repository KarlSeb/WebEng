package qasystem.web.dtos;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

/**
 * Kapselt die Daten für eine Antwort.
 */
public class AnswerDTO {
    private long id;

    @NotEmpty
    @NotNull
    private long parentQuestionId;

    private String text;

    private GregorianCalendar date;

    private boolean accepted;

    @NotEmpty
    @NotNull
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentQuestionId() {
        return parentQuestionId;
    }

    public void setParentQuestionId(long parentQuestionId) {
        this.parentQuestionId = parentQuestionId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
