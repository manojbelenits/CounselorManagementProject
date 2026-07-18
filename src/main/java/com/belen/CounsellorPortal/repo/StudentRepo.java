package com.belen.CounsellorPortal.repo;

import com.belen.CounsellorPortal.entity.Enrollment;
import com.belen.CounsellorPortal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Integer>, JpaSpecificationExecutor<Student> {

    @Query("select stud from Student stud where stud.studEmail=:email")
    public Optional<Student> findStudentByEmail(@Param("email") String email);
}
