package org.example.exambyte.application.dto;

import lombok.*;
import org.example.exambyte.domain.model.Answer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AnswersDto {

//    private List<AnswerDto> answers;

    private List<AnswerDto> answers = new ArrayList<>(); // Initialize the list here

}
