package org.example.exambyte.application.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TestResultDto {

    private Long id;
    private Long testId;
    private String takenBy;
    private List<AnswerDto> answers;
    private LocalDateTime submitDate;
    private Double score;
    private Boolean passed;
    private Boolean graded;
    private String correctedBy;

}
