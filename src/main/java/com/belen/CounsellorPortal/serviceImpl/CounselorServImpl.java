package com.belen.CounsellorPortal.serviceImpl;

import com.belen.CounsellorPortal.entity.Counselor;
import com.belen.CounsellorPortal.entity.Course;
import com.belen.CounsellorPortal.entity.Enrollment;
import com.belen.CounsellorPortal.entity.Student;
import com.belen.CounsellorPortal.repo.CounselorRepo;
import com.belen.CounsellorPortal.repo.CourseRepo;
import com.belen.CounsellorPortal.repo.EnrollmentRepo;
import com.belen.CounsellorPortal.repo.StudentRepo;
import com.belen.CounsellorPortal.requestDto.EnrollmentUpdateReqDto;
import com.belen.CounsellorPortal.requestDto.LoginReqDto;
import com.belen.CounsellorPortal.requestDto.RegisterCounselorReqDto;
import com.belen.CounsellorPortal.requestDto.RegisterEnrollmentReqDto;
import com.belen.CounsellorPortal.responseDto.CheckEnquiryResDto;
import com.belen.CounsellorPortal.responseDto.RegisterCounselorResDto;
import com.belen.CounsellorPortal.responseDto.ViewEnquiryResDto;
import com.belen.CounsellorPortal.specification.EnrollmentSpecification;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CounselorServImpl {

   private final StudentRepo studentRepo;
   private final CourseRepo courseRepo;
   private final CounselorRepo counselorRepo;
   private final EnrollmentRepo enrollmentRepo;
   private final EntityManager em;
   private final AuthenticationManager authenticationManager;


   @Transactional
   public Enrollment registerTheEnrollment(RegisterEnrollmentReqDto enrollmentReqDto){

       Optional<Student> studentByEmail = studentRepo.findStudentByEmail(enrollmentReqDto.getStudent().getStudEmail());
       if(studentByEmail.isEmpty()){
           Student student = studentRepo.save(enrollmentReqDto.getStudent());
           return  saveTheEnrollment(student, enrollmentReqDto);
       }

       if(!studentByEmail.get().getStudPhnNum().equals(enrollmentReqDto.getStudent().getStudPhnNum())){
           throw new RuntimeException("Phone Num is diff from original phnNum!!");
       }

       return saveTheEnrollment(studentByEmail.get(),enrollmentReqDto);
   }


   public Counselor counselorLogin(LoginReqDto loginReqDto){

       UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());

       Authentication authenticate = authenticationManager.authenticate(token);

       Counselor coun=null;

       try{
           if(authenticate.isAuthenticated()){
               coun= counselorRepo.findCounselorByEmail(loginReqDto.getEmail()).orElseThrow();
           }
       }
       catch (Exception e){

           throw new RuntimeException("Login Failed");
       }
        return coun;
   }



    public CheckEnquiryResDto checkEnquiryDetails(Integer counselorId){

       CheckEnquiryResDto resDto = new CheckEnquiryResDto(0L,0L,0L,0L);

        Counselor counselor = counselorRepo.findById(counselorId)
                .orElseThrow(() -> new RuntimeException("no Counselor Exist With that id"));
        List<Enrollment> enrollments = counselor.getEnrollments();

        if(enrollments.isEmpty())
            return resDto;

        long totalEnquires = enrollments.size();
        long openEnquires = enrollmentRepo.findCountOfStatus(counselorId, "open");
        long enrolledEnquires =enrollmentRepo.findCountOfStatus(counselorId,"enrolled");
        long lostEnquires =enrollmentRepo.findCountOfStatus(counselorId,"lost");

        resDto.setTotalEnquires(totalEnquires);
        resDto.setEnrolledEnquires(enrolledEnquires);
        resDto.setOpenEnquires(openEnquires);
        resDto.setLostEnquires(lostEnquires);
        return resDto;
    }


    public List<ViewEnquiryResDto> viewAllEnquires(Integer counselorId,String classMode, Integer courseId, String status){

         if(!counselorRepo.existsById(counselorId)){
             throw new RuntimeException("No Counselor present with that id");
         }

        Specification<Enrollment> spec = EnrollmentSpecification.hasCounselor(counselorId);

         if(classMode!=null){
             spec=spec.and(EnrollmentSpecification.hasClassMode(classMode));
         }
         if(courseId!=null){
             spec=spec.and(EnrollmentSpecification.hasCourseId(courseId));
         }
         if(status!=null){
             spec=spec.and(EnrollmentSpecification.hasStatus(status));
         }

        List<Enrollment> all = enrollmentRepo.findAll(spec);

        return  all.stream()
                .map(enrollment -> new ViewEnquiryResDto(
                        enrollment.getEnrollmentId(),
                        enrollment.getCourse().getCourseId(),
                        enrollment.getStudent().getStudName(),
                        enrollment.getStudent().getStudEmail(),
                        enrollment.getCourse().getCourseName(),
                        enrollment.getMode(),
                        enrollment.getStatus()
                ))
                .toList();


    }


    @Transactional
    public Enrollment updateTheEnrollment(EnrollmentUpdateReqDto reqDto){
        Enrollment enrollment = enrollmentRepo.findById(reqDto.getEnquiryId())
                .orElseThrow(() -> new RuntimeException("No EnrollmentPresent With That ID:" + reqDto.getEnquiryId()));

        Course course = em.getReference(Course.class, reqDto.getCourseId());
        enrollment.setCourse(course);
        enrollment.setMode(reqDto.getClassMode());
        enrollment.setStatus(reqDto.getStatus());
        return enrollment;
    }



    @Transactional
    public RegisterCounselorResDto registerCounselor(RegisterCounselorReqDto reqDto) {

        // Check email
        if (counselorRepo.findCounselorByEmail(reqDto.getCounselorEmail()).isPresent()) {
            throw new RuntimeException("Counselor already exists with email : "
                    + reqDto.getCounselorEmail());
        }

        // Check phone number
        if (counselorRepo.findCounselorByCounselorPhnNum(reqDto.getCounselorPhnNum()).isPresent()) {
            throw new RuntimeException("Counselor already exists with phone number : "
                    + reqDto.getCounselorPhnNum());
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


        //DTO -> Entity
        Counselor counselor = new Counselor();
        counselor.setCounselorName(reqDto.getCounselorName());
        counselor.setCounselorPhnNum(reqDto.getCounselorPhnNum());
        counselor.setCounselorEmail(reqDto.getCounselorEmail());
        counselor.setPassword(encoder.encode(reqDto.getPassword()));

        Counselor savedCounselor = counselorRepo.save(counselor);

        //Entity -> Response DTO
        RegisterCounselorResDto response = new RegisterCounselorResDto();
        response.setCounselorId(savedCounselor.getCounselorId());
        response.setCounselorName(savedCounselor.getCounselorName());
        response.setCounselorPhnNum(savedCounselor.getCounselorPhnNum());
        response.setCounselorEmail(savedCounselor.getCounselorEmail());

        return response;
    }



    private Enrollment saveTheEnrollment(Student std,RegisterEnrollmentReqDto enrollmentReqDto){
        Course course = courseRepo.findById(enrollmentReqDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("No Course Present With that Id:" + enrollmentReqDto.getCourseId()));

        Counselor counselor = counselorRepo.findCounselorByEmail(enrollmentReqDto.getCounselorEmail())
                .orElseThrow(() -> new RuntimeException("No Counselor Present With that email:" + enrollmentReqDto.getCounselorEmail()));

        Enrollment enrollment = new Enrollment();    //enrollment Object
        enrollment.setStatus(enrollmentReqDto.getStatus());
        enrollment.setMode(enrollmentReqDto.getMode());
        enrollment.setCounselor(counselor);
        enrollment.setStudent(std);
        enrollment.setCourse(course);
        return enrollmentRepo.save(enrollment);
    }


}
























