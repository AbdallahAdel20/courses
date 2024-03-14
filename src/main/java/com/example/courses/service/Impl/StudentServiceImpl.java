package com.example.courses.service.Impl;

import com.example.courses.dto.CourseDto;
import com.example.courses.dto.StudentDto;
import com.example.courses.entity.Course;
import com.example.courses.entity.Student;
import com.example.courses.exception.DaplicateRecoredException;
import com.example.courses.exception.EmptyResourceException;
import com.example.courses.exception.ResourceNotFoundException;
import com.example.courses.mapper.StudentMapper;
import com.example.courses.repository.CourseRepository;
import com.example.courses.repository.StudentRepository;
import com.example.courses.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class StudentServiceImpl implements StudentService {



    private  final   StudentRepository studentRepository;

    private  final    StudentMapper studentMapper;

    private final CourseRepository courseRepository;

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        if (studentDto.getFirstName() == null || studentDto.getLastName() == null ||
                studentDto.getFirstName().isEmpty() || studentDto.getLastName().isEmpty()) {
            throw new EmptyResourceException("Student should not be empty!");
        }

        Student student = studentMapper.mapToEntity(studentDto);

        Student savedStudent = studentRepository.save(student);

        return studentMapper.mapToDto(savedStudent);
    }

    public Student findStudent(Long id){
        return studentRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Student with id "+id+" Not found!"));

    }


    @Override
    public StudentDto getStudentById(Long id) {

        Student student = findStudent(id);

        return studentMapper.mapToDto(student);
    }

    @Override
    public List<Student> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        System.out.println(students);

        return students;
//        return students.stream()
//                .map((studentMapper::mapToSmallDto))
//                .collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto, Long id) {
        if (studentDto.getFirstName() == null || studentDto.getLastName() == null ||
                studentDto.getFirstName().isEmpty() || studentDto.getLastName().isEmpty()) {
            throw new EmptyResourceException("Student should not be empty!");
        }

        Student foundStudent = findStudent(id);

        Student newStudent = studentMapper.mapToEntity(studentDto);

        foundStudent.setFirstName(newStudent.getFirstName());
        foundStudent.setLastName(newStudent.getLastName());
//        foundStudent.setCourses(newStudent.getCourses());

        studentRepository.save(foundStudent);

        return studentMapper.mapToDto(foundStudent);
    }

    @Override
    public void DeleteStudentById(Long id) {
        Student foundStudent = findStudent(id);

        studentRepository.deleteById(id);

    }

    @Override
    public StudentDto addCourseToStudent(Long studentId , Long courseId){
        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(()->new ResourceNotFoundException("Course with id "+courseId+" Not Found!"));

        Student foundStudent = findStudent(studentId);

        if(foundStudent.addCourse(foundCourse)){
            studentRepository.save(foundStudent);
            return studentMapper.mapToDto(foundStudent);
        }else {
            throw new DaplicateRecoredException("Course with name "+foundCourse.getName()+
                    " is given to "+ foundStudent.getFirstName());

        }
    }

    @Override
    public StudentDto deleteCourseFromStudent(Long studentId, Long courseId) {
        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(()->new ResourceNotFoundException("Course with id "+courseId+" Not Found!"));

        Student foundStudent = findStudent(studentId);

        if(foundStudent.deleteCourse(foundCourse)){
            studentRepository.save(foundStudent);
            return studentMapper.mapToDto(foundStudent);
        }else{
            throw new ResourceNotFoundException("Course with name "+foundCourse.getName()+" not found ");
        }

    }
}
