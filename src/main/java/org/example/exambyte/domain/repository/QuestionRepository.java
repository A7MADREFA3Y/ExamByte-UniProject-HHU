package org.example.exambyte.domain.repository;


import org.example.exambyte.domain.model.Question;

public interface QuestionRepository  {

    Question findById(Long id);

}
