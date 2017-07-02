package qasystem.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import qasystem.persistence.entities.Question;
import qasystem.persistence.entities.User;

import java.util.Collection;

/**
 * Repository für die Fragen, die gestellt worden sind
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "select q from Question q where q.answered = false")
    Collection<Question> findAllByAnsweredNot();

    //q.user.id so möglich?
    @Query(value = "select q from Question q where q.user.id = :UserId")
    Collection<Question> findAllByUserId(@Param("UserId") long UserId);

    // why dis no work
//    @Query(value = "select q from Question q except (select distinct q2 from Question q2, Answer a where a.parentQuestion = q2)")
//    Collection<Question> findAllByNoReply();

    @Query(value = "update Question q set q.answered = :answered where q.id = :questionId")
    Collection<Question> updateAnswered(@Param("questionId") long questionId, @Param("answered") boolean answered);

//    @Query(value = "select q from Question q, Answer a where a.id = :id and a.parentQuestion.id = q.id")
//    Collection<Question> findAllByAnswersContainsUserId(@Param("id") long id);


}
