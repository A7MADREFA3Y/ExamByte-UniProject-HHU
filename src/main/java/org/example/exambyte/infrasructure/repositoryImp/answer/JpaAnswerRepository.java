package org.example.exambyte.infrasructure.repositoryImp.answer;

import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface JpaAnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.takenBy LIKE :username")
    List<Answer> findAllAnswersByUserName(@Param("username") String username);
}
