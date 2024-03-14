package com.example.courses.dto;

import java.util.HashSet;
import java.util.Set;

public class StudentDto {


    private Long id;

    private String firstName;

    private String lastName;

    private Set<CourseDto> coursesDto ;

    public StudentDto() {
    }

    public StudentDto(String firstName, String lastName, Set<CourseDto> coursesDto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.coursesDto = coursesDto;
    }

    public StudentDto(Long id, String firstName, String lastName, Set<CourseDto> coursesDto) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coursesDto = coursesDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<CourseDto> getCoursesDto() {
        return coursesDto;
    }

    public void setCoursesDto(Set<CourseDto> coursesDto) {
        this.coursesDto = coursesDto;
    }
}
