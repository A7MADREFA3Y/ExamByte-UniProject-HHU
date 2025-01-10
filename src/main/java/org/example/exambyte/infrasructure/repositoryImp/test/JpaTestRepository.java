package org.example.exambyte.infrasructure.repositoryImp.test;

import org.example.exambyte.domain.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTestRepository extends JpaRepository<Test , Long> {
}
