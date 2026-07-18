package com.belen.CounsellorPortal.repo;

import com.belen.CounsellorPortal.entity.Counselor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CounselorRepo extends JpaRepository<Counselor,Integer> {

    @Query("select couns from Counselor couns where couns.counselorEmail=:email")
    public Optional<Counselor> findCounselorByEmail(@Param("email") String email);


    Optional<Counselor> findCounselorByCounselorPhnNum(String counselorPhnNum);








}
