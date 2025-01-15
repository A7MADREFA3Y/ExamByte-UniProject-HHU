package org.example.exambyte.infrasructure.repositoryImp.question;

import org.example.exambyte.domain.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuestionRepository extends JpaRepository<Question, Long> {
}
