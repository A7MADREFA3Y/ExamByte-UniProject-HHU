package org.example.exambyte.application.dto;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AnswersDto {

    private List<AnswerDto> answers = new ArrayList<>();

}
