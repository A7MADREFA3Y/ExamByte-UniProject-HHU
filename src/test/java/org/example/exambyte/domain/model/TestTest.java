package org.example.exambyte.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TestTest {

    @Test
    @DisplayName("Test can have an Id")
    public void testMustHaveId() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        test.setId(14L);

        assertThat(test.getId()).isEqualTo(14L);
    }

    @Test
    @DisplayName("Test can have an name")
    public void testMustHaveName() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        test.setTestName("Propra 2");

        assertThat(test.getTestName()).isEqualTo("Propra 2");
    }

    @Test
    @DisplayName("Test can have an start time")
    public void testMustHaveStartTime() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        LocalDateTime startTime = LocalDateTime.now();

        test.setStartTime(startTime);

        assertThat(test.getStartTime()).isEqualTo(startTime);
    }

    @Test
    @DisplayName("Test can have an end time")
    public void testMustHaveEndTime() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        LocalDateTime endTime = LocalDateTime.now().plusDays(7);

        test.setEndTime(endTime);

        assertThat(test.getEndTime()).isEqualTo(endTime);
    }

    @Test
    @DisplayName("Test can have an Result time")
    public void testMustHaveResultPublicationTime() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        LocalDateTime resultday = LocalDateTime.now().plusDays(14);

        test.setResultPublicationTime(resultday);

        assertThat(test.getResultPublicationTime()).isEqualTo(resultday);
    }

    @Test
    @DisplayName("Test can have an end time")
    public void testMustBeCreatedBySomeone() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        test.setCreatedBy("A7MADREFA3Y");

        assertThat(test.getCreatedBy()).isEqualTo("A7MADREFA3Y");
    }


    @Test
    @DisplayName("Test can have an end time")
    public void testMustHaveAllOfTheAttributes() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusDays(7);
        LocalDateTime resultTime = LocalDateTime.now().plusDays(14);

        org.example.exambyte.domain.model.Test test = org.example.exambyte.domain.model.Test
                .builder()
                .testName("Propra 2")
                .id(13L)
                .startTime(startTime)
                .endTime(endTime)
                .resultPublicationTime(resultTime)
                .createdBy("A7MADREFA3Y")
                .build();


        assertThat(test.getTestName()).isEqualTo("Propra 2");
        assertThat(test.getId()).isEqualTo(13L);
        assertThat(test.getStartTime()).isEqualTo(startTime);
        assertThat(test.getEndTime()).isEqualTo(endTime);
        assertThat(test.getResultPublicationTime()).isEqualTo(resultTime);
        assertThat(test.getCreatedBy()).isEqualTo("A7MADREFA3Y");

    }






}




























