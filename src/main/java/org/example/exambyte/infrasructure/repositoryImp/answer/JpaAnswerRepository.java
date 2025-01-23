package org.example.exambyte.infrasructure.repositoryImp.answer;

import org.example.exambyte.domain.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaAnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.takenBy LIKE :username")
    List<Answer> findAllAnswersByUserName(@Param("username") String username);


    @Query("SELECT a FROM Answer a WHERE a.testId = :testId AND a.takenBy LIKE :username")
    List<Answer> findAllAnswersByTestIdWithUsername(@Param("testId")Long testId, @Param("username")String username);

    @Query("SELECT a FROM Answer a WHERE LENGTH(a.answerText) > 2 AND a.testId = :testId AND a.takenBy LIKE :username")
    List<Answer> getAllAnswersForFreeText(@Param("testId") Long testId, @Param("username") String username);

    @Query("SELECT a FROM Answer a where a.testId = :testId AND a.questionId = :questionId")
    Answer findAnswerByTestAndQuestionId(@Param("testId") Long testId,@Param("questionId") Long questionId);
}
