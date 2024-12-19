package org.example.exambyte.model;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Setter
@Getter
@Data
@Table(name = "Test")
@Entity
public class TestsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nullable
    private String testName;

//    @CreationTimestamp
//    private LocalDateTime createdOn;
//
//    private String description;
//
//    private String CreatedBy;

}




















