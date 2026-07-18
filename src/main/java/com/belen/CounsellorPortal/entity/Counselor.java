package com.belen.CounsellorPortal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@RequiredArgsConstructor
public class Counselor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer counselorId;

    @NonNull
    private String counselorName;

    @NonNull
    private String counselorPhnNum;

    @NonNull
    private String counselorEmail;

    @NonNull
    private String password;

    @OneToMany(mappedBy = "counselor")
    @JsonIgnore
    private List<Enrollment> enrollments;
}

