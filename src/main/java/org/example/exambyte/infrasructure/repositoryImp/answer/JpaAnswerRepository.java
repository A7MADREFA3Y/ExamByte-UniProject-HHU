package org.example.exambyte.infrasructure.repositoryImp.answer;

import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface JpaAnswerRepository extends JpaRepository<Answer, Long> {
}
