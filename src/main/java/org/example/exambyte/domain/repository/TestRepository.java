package org.example.exambyte.domain.repository;

import org.example.exambyte.domain.model.Test;
import org.example.exambyte.domain.model.User;

import java.util.List;

public interface TestRepository {

    void save(Test test);

    void deleteById(Long testId);

    Test findById(Long testId);

    List<Test> findAll();
}
