package com.belen.CounsellorPortal.repo;

import com.belen.CounsellorPortal.entity.Enrollment;
import com.belen.CounsellorPortal.responseDto.ViewEnquiryResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepo extends JpaRepository<Enrollment,Integer>, JpaSpecificationExecutor<Enrollment> {




    @Query("select COUNT(e) from Enrollment e where e.counselor.counselorId=:counselorId and e.status = :status")
    public long findCountOfStatus(@Param("counselorId")Integer counselorId,
                                     @Param("status") String status);

//
}
