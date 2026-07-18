package com.belen.CounsellorPortal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@RequiredArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer enrollmentId;

    @ManyToOne
    @JoinColumn(name="studentId")
//    @JsonIgnore
    private Student student;

    @ManyToOne
    @JoinColumn(name="courseId")
//    @JsonIgnore
    private Course course;

    @ManyToOne
    @JoinColumn(name="counselorId")
//    @JsonIgnore
    private Counselor counselor;

    @NonNull
    private String mode;

    @NonNull
    private String status;
}
