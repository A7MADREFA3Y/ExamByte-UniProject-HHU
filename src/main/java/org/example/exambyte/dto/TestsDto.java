package org.example.exambyte.dto;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.exambyte.model.Question;
import org.example.exambyte.model.TestResult;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@Valid
public class TestsDto {

    private Long id;

    @NotNull
    @NotEmpty(message = "please Insert Test Name")
    private String testName;

    private String createdBy;

    @NotNull(message = "please Insert starting time")
    private LocalDateTime startTime;

    @NotNull(message = "please Insert deadline time")
    private LocalDateTime endTime;

    @NotNull(message = "Result publication time is required")
    private LocalDateTime resultPublicationTime;

    private List<Question> questions;
    private List<TestResult> results;


}
