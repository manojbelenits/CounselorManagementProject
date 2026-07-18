package com.belen.CounsellorPortal.responseDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCounselorResDto {

    private Integer counselorId;

    private String counselorName;

    private String counselorPhnNum;

    private String counselorEmail;

}
