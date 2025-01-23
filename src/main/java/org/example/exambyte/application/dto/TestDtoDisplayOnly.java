package org.example.exambyte.application.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TestDtoDisplayOnly {

    private Long id;
    private String testName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime resultPublicationTime;
    private String remainTime;
    private boolean expired;
    private boolean submitted;
    private boolean graded = false;

}
