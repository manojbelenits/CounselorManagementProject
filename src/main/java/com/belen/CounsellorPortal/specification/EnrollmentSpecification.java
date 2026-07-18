package com.belen.CounsellorPortal.specification;

import com.belen.CounsellorPortal.entity.Enrollment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class EnrollmentSpecification {


    public static Specification<Enrollment> hasCourseId(Integer courseId){

        return (root, query, cb) -> {
            return  cb.equal(root.get("course").get("courseId"), courseId);
        };

    }

    public static Specification<Enrollment> hasCounselor(Integer counselorId){

        return (root, query, cb) -> {
            return  cb.equal(root.get("counselor").get("counselorId"), counselorId);
        };

    }


    public static Specification<Enrollment> hasClassMode(String classMode){
        return (root, query, cb) -> {
            return cb.equal(root.get("mode"),classMode);
        };
    }


    public static  Specification<Enrollment> hasStatus(String status){
        return (root, query, cb) -> {
            return cb.equal(root.get("status"),status);
        };
    }
}
