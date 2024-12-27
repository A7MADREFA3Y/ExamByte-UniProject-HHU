package org.example.exambyte.repo;


// this part of the code is still under working for the Repository


import org.example.exambyte.model.Tests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestsRepository extends JpaRepository<Tests, Long> {}

