package org.example.exambyte.infrasructure.repositoryImp.testResult;

import org.example.exambyte.domain.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaTestResultRepository extends JpaRepository<TestResult, Long> {

    @Query("SELECT t FROM TestResult t WHERE t.testId = :testId AND t.takenBy LIKE :username")
    List<TestResult> findAllByTestIdAndUsername(Long testId, String username);

    TestResult findTestResultByTestIdAndTakenBy(Long testId, String takenBy);
}
