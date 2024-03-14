package com.example.courses.mapper;

import com.example.courses.dto.CourseDto;
import com.example.courses.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CourseMapper {



    @Mapping(source = "name" , target = "name")
    @Mapping(source = "description" , target = "description")
//    @Mapping(source = "studentsDto" , target = "students")
    Course mapToEntity(CourseDto courseDto);


    @Mapping(source = "name" , target = "name" )
    @Mapping(source = "description" , target = "description")
//    @Mapping(source = "students" , target = "studentsDto")
    CourseDto mapToDto(Course course);
}
