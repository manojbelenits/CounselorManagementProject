package com.belen.CounsellorPortal.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCounselorReqDto {

    @NotBlank
    private String counselorName;

    @NotNull
    @Pattern(regexp = "^[6789][0-9]{9}$")
    private String counselorPhnNum;

    @Email
    @NotBlank
    private String counselorEmail;

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[A-Za-z0-9@#$%]{8,16}$")
    private String password;

}
