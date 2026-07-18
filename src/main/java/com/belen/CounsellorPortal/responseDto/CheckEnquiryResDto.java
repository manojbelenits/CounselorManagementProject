package com.belen.CounsellorPortal.responseDto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CheckEnquiryResDto {
    private Long totalEnquires;
    private Long openEnquires;
    private Long enrolledEnquires;
    private Long lostEnquires;
}
