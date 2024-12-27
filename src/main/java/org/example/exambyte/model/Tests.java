package org.example.exambyte.model;



// this part of the code is still under working for the Repository

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Test")
@Entity
public class Tests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nullable
    private String testName;

}
