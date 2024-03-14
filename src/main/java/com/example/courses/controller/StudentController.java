package com.example.courses.controller;

import com.example.courses.dto.CourseDto;
import com.example.courses.dto.StudentDto;
import com.example.courses.entity.Student;
import com.example.courses.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/")
@CrossOrigin("*")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;



    @GetMapping("all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> student = studentService.getAllStudent();
        return new  ResponseEntity<>(student, HttpStatus.OK);


    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id){
        StudentDto studentDto = studentService.getStudentById(id);
        return new ResponseEntity<>(studentDto , HttpStatus.OK);

    }

    @PostMapping("add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto){
        StudentDto savedStudentDto = studentService.addStudent(studentDto);
        return new ResponseEntity<>(savedStudentDto , HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto ,@PathVariable Long id ){
        StudentDto updatedStudentDto = studentService.updateStudent(studentDto,id);
        return new ResponseEntity<>(updatedStudentDto , HttpStatus.OK);
    }


    @PutMapping("addCourseToStudent/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> addCourseToStudent(@RequestBody CourseDto course, @PathVariable Long id ){
        StudentDto updatedStudentDto = studentService.addCourseToStudent(id , course.getId());
        return new ResponseEntity<>(updatedStudentDto , HttpStatus.OK);
    }

    @PutMapping("deleteCourseFromStudent/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> deleteCourseFromStudent(@RequestBody CourseDto course, @PathVariable Long id ){
        StudentDto updatedStudentDto = studentService.deleteCourseFromStudent(id , course.getId());
        return new ResponseEntity<>(updatedStudentDto , HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable Long id){
        studentService.DeleteStudentById(id);
    }


}

