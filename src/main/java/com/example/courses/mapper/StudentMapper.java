package com.example.courses.mapper;

import com.example.courses.dto.StudentDto;
import com.example.courses.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;



@Mapper
public interface StudentMapper {


//    map student dto to entity
    @Mapping(source = "firstName" , target = "firstName")
    @Mapping(source = "lastName" , target = "lastName")
    @Mapping(source = "coursesDto" , target = "courses")
    Student mapToEntity (StudentDto studentDto);

//    map student entity to dto
    @Mapping(target = "firstName" , source = "firstName")
    @Mapping(target = "lastName" , source = "lastName")
    @Mapping(target = "coursesDto" , source = "courses")
    StudentDto mapToDto (Student student);

    @Mapping(source = "firstName" , target = "firstName")
    @Mapping(source = "lastName" , target = "lastName")
//    @Mapping(source = "coursesDto" , target = "courses")
    Student mapToSmallEntity (StudentDto studentDto);

    //    map student entity to dto
    @Mapping(target = "firstName" , source = "firstName")
    @Mapping(target = "lastName" , source = "lastName")
//    @Mapping(target = "coursesDto" , source = "courses")
    StudentDto mapToSmallDto (Student student);
}
