package com.belen.CounsellorPortal.requestDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EnrollmentUpdateReqDto {

    @Positive
    @NotNull
    private Integer enquiryId;

    @Positive
    @NotNull
    private Integer courseId;

    @NotBlank
    private String classMode;

    @NotBlank
    private String status;

}
