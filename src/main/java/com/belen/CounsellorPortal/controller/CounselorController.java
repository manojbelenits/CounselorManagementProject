package com.belen.CounsellorPortal.controller;

import com.belen.CounsellorPortal.entity.Counselor;
import com.belen.CounsellorPortal.entity.Enrollment;
import com.belen.CounsellorPortal.requestDto.EnrollmentUpdateReqDto;
import com.belen.CounsellorPortal.requestDto.LoginReqDto;
import com.belen.CounsellorPortal.requestDto.RegisterCounselorReqDto;
import com.belen.CounsellorPortal.requestDto.RegisterEnrollmentReqDto;
import com.belen.CounsellorPortal.responseDto.CheckEnquiryResDto;
import com.belen.CounsellorPortal.responseDto.RegisterCounselorResDto;
import com.belen.CounsellorPortal.responseDto.ViewEnquiryResDto;
import com.belen.CounsellorPortal.serviceImpl.CounselorServImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CounselorController {

    private final CounselorServImpl servImpl;


    @PostMapping("/addEnrollmemnt")
    public ResponseEntity<Enrollment>  addAEnrollment(@Valid @RequestBody RegisterEnrollmentReqDto reqDto){
        Enrollment enrollment = servImpl.registerTheEnrollment(reqDto);
        return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
    }


    @GetMapping("/login")
    public ResponseEntity<Counselor> loginEndPoint(@Valid @RequestBody LoginReqDto loginReqDto){
        Counselor counselor = servImpl.counselorLogin(loginReqDto);

        return new ResponseEntity<>(counselor,HttpStatus.OK);
    }


    @GetMapping("/getEnquiryDetails/{counselorId}")
    public ResponseEntity<CheckEnquiryResDto> displayEnquiryDetails(@PathVariable Integer counselorId){
        CheckEnquiryResDto resDto = servImpl.checkEnquiryDetails(counselorId);
        return new ResponseEntity<>(resDto,HttpStatus.OK);
    }


    @GetMapping("/getCounselorEnquires")
    public ResponseEntity<List<ViewEnquiryResDto>> getCounselorEnquires_SearchFilter(@RequestParam(value="counselorId") Integer counselorId,
                                                                  @RequestParam(value="courseId",required = false) Integer courseID,
                                                                  @RequestParam(value="classMode",required = false) String mode,
                                                                  @RequestParam(value="status",required = false) String status){
        List<ViewEnquiryResDto> viewEnquiryResDtos = servImpl.viewAllEnquires(counselorId, mode, courseID, status);

        return new ResponseEntity<>(viewEnquiryResDtos,HttpStatus.OK);
    }


    @PostMapping("/updateEnrollment")
    public ResponseEntity<Enrollment> updateEnrollmentEndPoint(@Valid @RequestBody EnrollmentUpdateReqDto reqDto){
        Enrollment enrollment = servImpl.updateTheEnrollment(reqDto);
        return new ResponseEntity<>(enrollment,HttpStatus.CREATED);
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterCounselorResDto> registerCounselor(
            @Valid @RequestBody RegisterCounselorReqDto reqDto) {

        RegisterCounselorResDto response =
                servImpl.registerCounselor(reqDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }





}
