package org.example.exambyte.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
public class TestsDto {
    private Long id;
    private String testName;
    private String createdBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime resultPublicationTime;

}
