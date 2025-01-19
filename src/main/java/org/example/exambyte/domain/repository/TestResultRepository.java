package org.example.exambyte.domain.repository;

import org.example.exambyte.application.dto.TestResultDto;
import org.example.exambyte.domain.model.TestResult;

public interface TestResultRepository {

    void saveTestResult(TestResult testResult);
}
