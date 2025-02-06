package org.example.exambyte.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Valid
public class TestsDto {

    private Long id;

    @NotNull(message = "please Insert Test Name")
    private String testName;

    private String createdBy;

    @NotNull(message = "please Insert starting time")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime resultPublicationTime;

    public TestsDto(Long id, String testName) {
        this.id = id;
        this.testName = testName;
    }
}
