package com.shouwn.oj.service.problem;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.enums.ProblemType;
import com.shouwn.oj.repository.member.AdminRepository;
import com.shouwn.oj.repository.member.StudentRepository;
import com.shouwn.oj.repository.problem.CourseRepository;
import com.shouwn.oj.repository.problem.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {
    private AdminRepository adminRepository;
    private StudentRepository studentRepository;
    private ProblemRepository problemRepository;
    private CourseRepository courseRepository;

    /**
     * 문제 리스트 - GET
     * @param courseId, problemType, requesterId
     * @return 해당 유저의 문제 목록
     */
    public List<Problem> getProblemList(Long courseId, ProblemType problemType, Long requesterId){

        Optional<Admin> adminOptional = adminRepository.findById(requesterId);
        List<Course> courses = null;
        if(adminOptional.isPresent()){
            Admin admin = adminOptional.get();
            courses = admin.getCourses();
        }else{
            Student student = studentRepository.findById(requesterId).get();
            courses = student.getCourses();
        }

        Course course = null;

        for(int i=0; i<courses.size(); ++i){
            if(courses.get(i).getId()==courseId){
                course = courses.get(i);
                break;
            }
        }

        List<Problem> problems = problemRepository.findByCourseIdAndByType(courseId,problemType);

        return problems;
    }
}
