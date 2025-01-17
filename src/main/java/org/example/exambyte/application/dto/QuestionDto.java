package org.example.exambyte.application.dto;


import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.exambyte.domain.model.QuestionType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//@Valid //for later
public class QuestionDto {

    private Long id;

    private Long testId;

    @NotBlank(message = "Question text is required")
    private String questionText;

    private QuestionType questionType;

//    @Size(min = 2, message = "At least two options are required")
//    private List<String> options;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String correctAnswer;



}
