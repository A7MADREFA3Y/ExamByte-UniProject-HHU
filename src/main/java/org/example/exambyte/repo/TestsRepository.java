package org.example.exambyte.repo;


import org.example.exambyte.model.TestsModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestsRepository extends CrudRepository<TestsModel, String> {}