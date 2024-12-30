package org.example.exambyte.repo;


// this part of the code is still under working for the Repository


import org.example.exambyte.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestsRepository extends JpaRepository<Test, Long> {}

