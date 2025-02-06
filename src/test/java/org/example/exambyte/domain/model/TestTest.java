package org.example.exambyte.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TestTest {

    @Test
    @DisplayName("Test must have an Id")
    public void testMustHaveId() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        test.setId(14L);

        assertThat(test.getId()).isEqualTo(14L);
    }



    @Test
    @DisplayName("Test cant have an Id as null")
    public void testCantHaveIdAsAull() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        try {
        test.setId(null);
        }catch (Exception e) {}

        assertThat(test.getId()).isNull();
    }



    @Test
    @DisplayName("Test must an name")
    public void testMustHaveName() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        test.setTestName("Propra 2");

        assertThat(test.getTestName()).isEqualTo("Propra 2");
    }



    @Test
    @DisplayName("Test must have an start time")
    public void testMustHaveStartTime() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        LocalDateTime startTime = LocalDateTime.now();

        test.setStartTime(startTime);

        assertThat(test.getStartTime()).isEqualTo(startTime);
    }

    @Test
    @DisplayName("Test must have an start time")
    public void testCantHaveStartTimeAsNull() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        try {
            test.setStartTime(null);
        }catch (Exception e) {}

        assertThat(test.getStartTime()).isNull();
    }





    @Test
    @DisplayName("Test must have an end time")
    public void testMustHaveEndTime() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        LocalDateTime endTime = LocalDateTime.now().plusDays(7);

        test.setEndTime(endTime);

        assertThat(test.getEndTime()).isEqualTo(endTime);
    }




    @Test
    @DisplayName("Test Cant have an end time as null")
    public void testCantHaveEndTimeAsNull() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        try {
            test.setEndTime(null);
        }catch (Exception e) {}

        assertThat(test.getEndTime()).isNull();
    }



    @Test
    @DisplayName("Test Must have an Result time")
    public void testMustHaveResultPublicationTime() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        LocalDateTime resultantly = LocalDateTime.now().plusDays(14);

        test.setResultPublicationTime(resultantly);

        assertThat(test.getResultPublicationTime()).isEqualTo(resultantly);
    }



    @Test
    @DisplayName("Test cant have an Result time")
    public void testCantHaveResultPublicationTimeAsNull() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        try {
            test.setResultPublicationTime(null);
        }catch (Exception e) {}

        assertThat(test.getResultPublicationTime()).isNull();
    }




    @Test
    @DisplayName("Test must have an name of some that created the test ")
    public void testMustBeCreatedBySomeone() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        test.setCreatedBy("A7MADREFA3Y");

        assertThat(test.getCreatedBy()).isEqualTo("A7MADREFA3Y");
    }

    @Test
    @DisplayName("Test Cant have an name of some that created the test As null")
    public void testCantBeCreatedBySomeoneAsNull() {
        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();

        try {
            test.setCreatedBy(null);
        }catch (Exception e) {}

        assertThat(test.getCreatedBy()).isNull();
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




























