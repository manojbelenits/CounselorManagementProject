package com.belen.CounsellorPortal.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ViewEnquiryResDto {

    private Integer enquiryId;

    private Integer courseId;

    private String studName;

    private String studEmail;

    private String courseName;

    private String classMode;

    private String status;
}
