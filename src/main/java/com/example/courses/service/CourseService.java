package com.example.courses.service;

import com.example.courses.dto.CourseDto;

import java.util.List;

public interface CourseService {

    CourseDto addCourse (CourseDto courseDto);

    CourseDto getCourseById(Long id);

    List<CourseDto> getAllCourses();

    CourseDto updateCourse(CourseDto courseDto, Long id);

    void DeleteCourseById(Long id);
}
