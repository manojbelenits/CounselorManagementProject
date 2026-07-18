package com.belen.CounsellorPortal.repo;

import com.belen.CounsellorPortal.entity.Course;
import com.belen.CounsellorPortal.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course,Integer> {
}
