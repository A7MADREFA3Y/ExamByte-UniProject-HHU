package org.example.exambyte.infrasructure.repositoryImp.question;

import org.example.exambyte.domain.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaQuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTestId(Long testId);

    @Query("SELECT q FROM Question q WHERE q.testId = :testId AND q.questionType = 'FREE_TEXT'")
    List<Question> findByTestIdAndHaveTypeAsFREE_TEXT(@Param("testId") Long testId);

}
