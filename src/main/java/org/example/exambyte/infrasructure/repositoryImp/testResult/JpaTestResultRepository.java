package org.example.exambyte.infrasructure.repositoryImp.testResult;

import org.example.exambyte.domain.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTestResultRepository extends JpaRepository<TestResult, Long> {
}
