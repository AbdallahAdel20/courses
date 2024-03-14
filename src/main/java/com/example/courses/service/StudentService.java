package com.example.courses.service;

import com.example.courses.dto.StudentDto;
import com.example.courses.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;


public interface StudentService {

    StudentDto addStudent (StudentDto studentDto);

    StudentDto getStudentById(Long id);

    List<Student> getAllStudent();

    StudentDto updateStudent(StudentDto studentDto, Long id);

    StudentDto addCourseToStudent(Long studentId , Long courseId);

    StudentDto deleteCourseFromStudent(Long studentId , Long courseId);
    void DeleteStudentById(Long id);


}
