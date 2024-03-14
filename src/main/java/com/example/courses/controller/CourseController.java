package com.example.courses.controller;

import com.example.courses.dto.CourseDto;

import com.example.courses.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CourseDto>> getAllStudents() {
        List<CourseDto> courseDtos = courseService.getAllCourses();
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);

    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        CourseDto courseDto = courseService.getCourseById(id);
        return new ResponseEntity<>(courseDto, HttpStatus.OK);
    }

    @PostMapping("add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDto) {
        CourseDto newCourse = courseService.addCourse(courseDto);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDto, @PathVariable Long id) {
        CourseDto updateCourseDto = courseService.updateCourse(courseDto, id);
        return new ResponseEntity<>(updateCourseDto, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCourse(@PathVariable Long id) {
        courseService.DeleteCourseById(id);
    }


}
