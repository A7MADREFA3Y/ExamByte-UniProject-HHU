package org.example.exambyte.application.dto;


import lombok.*;
import org.example.exambyte.domain.model.Question;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AnswerDto {

    private Long questionId;

    private Long testId;

    private String answerText;

    private String takenBy;

}
