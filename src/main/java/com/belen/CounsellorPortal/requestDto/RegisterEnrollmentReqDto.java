package com.belen.CounsellorPortal.requestDto;

import com.belen.CounsellorPortal.entity.Student;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEnrollmentReqDto {

    @Valid
    private Student student;

    @NotNull
    @Positive
    private Integer courseId;

    @Email
    @NotBlank
    private String counselorEmail;

    @NotBlank
    private String mode;

    @NotBlank
    private String status;
}
