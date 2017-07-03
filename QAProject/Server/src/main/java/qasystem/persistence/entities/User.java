package qasystem.persistence.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

/**
 * Kapselt die Daten eines Benutzers für die Java Persistence API
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, targetEntity=Answer.class, mappedBy="user")
    private Collection<Answer> answers = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity=Question.class, mappedBy="user")
    private Collection<Question> questions = new LinkedList<>();

    protected User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, firstName='%s', password='%s']",
                id, username, password);
    }
}
