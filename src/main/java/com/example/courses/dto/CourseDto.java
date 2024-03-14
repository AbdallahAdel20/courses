package com.example.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CourseDto {

    private Long id;

    private String name;

    private String description;

//    private Set<StudentDto> studentsDto ;
//
//    public CourseDto() {
//    }
//
//    public CourseDto(String name, String description, Set<StudentDto> studentsDto) {
//        this.name = name;
//        this.description = description;
//        this.studentsDto = studentsDto;
//    }
//
//    public CourseDto(Long id, String name, String description, Set<StudentDto> studentsDto) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.studentsDto = studentsDto;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Set<StudentDto> getStudentsDto() {
//        return studentsDto;
//    }
//
//    public void setStudentsDto(Set<StudentDto> studentsDto) {
//        this.studentsDto = studentsDto;
//    }
}
