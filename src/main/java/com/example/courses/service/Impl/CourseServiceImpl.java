package com.example.courses.service.Impl;

import com.example.courses.dto.CourseDto;
import com.example.courses.entity.Course;
import com.example.courses.entity.Student;
import com.example.courses.exception.DaplicateRecoredException;
import com.example.courses.exception.EmptyResourceException;
import com.example.courses.exception.ResourceNotFoundException;
import com.example.courses.mapper.CourseMapper;
import com.example.courses.repository.CourseRepository;
import com.example.courses.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;


    public Course findCourse(Long id) {
        return courseRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " not found!"));
    }

    public Boolean courseExist(String name) {

        Optional<Course> foundCourse = courseRepository.findByName(name);
        if (foundCourse.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public CourseDto addCourse(CourseDto courseDto) {
        if (courseDto.getName() == null || courseDto.getDescription() == null ||
                courseDto.getName().isEmpty() || courseDto.getDescription().isEmpty()) {
            throw new EmptyResourceException("Course should not be empty!");
        }
        Course course = courseMapper.mapToEntity(courseDto);
        if (courseExist(course.getName())) {
            throw new DaplicateRecoredException("Course with name " + course.getName() + " is exist");
        }
        Course savedCourse = courseRepository.save(course);
        return courseMapper.mapToDto(savedCourse);
    }


    @Override
    public CourseDto getCourseById(Long id) {
        Course course = findCourse(id);

        return courseMapper.mapToDto(course);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map((courseMapper::mapToDto))
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto, Long id) {
        if (courseDto.getName() == null || courseDto.getDescription() == null ||
                courseDto.getName().isEmpty() || courseDto.getDescription().isEmpty()) {
            throw new EmptyResourceException("Course should not be empty!");
        }
        Course foundCourse = findCourse(id);
        Course newCourse = courseMapper.mapToEntity(courseDto);

        foundCourse.setDescription(newCourse.getDescription());
        foundCourse.setName(newCourse.getName());

        courseRepository.save(foundCourse);
        return courseMapper.mapToDto(foundCourse);
    }

    @Override
    public void DeleteCourseById(Long id) {
        Course course = findCourse(id);

        for (Student student : course.getStudents()) {
            student.getCourses().remove(course);
        }
        courseRepository.deleteById(id);

    }
}
