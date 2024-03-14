package com.example.courses.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

//import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "student_id")
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToMany()
    @JoinTable(name = "students_courses",
            joinColumns = @JoinColumn(name = "student_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id" , referencedColumnName = "id")
    )
//    @JsonManagedReference
    @JsonIgnoreProperties("students")
    private Set<Course> courses = new HashSet<>();



    public Student(String firstName, String lastName, Set<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
    }

    public Student() {
    }

    public Student(Long id, String firstName, String lastName, Set<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Boolean addCourse (Course course){
        if(this.courses.contains(course)){
            return false;
        }else {

            this.courses.add(course);
            return true;
        }


    }

    public Boolean deleteCourse (Course course){
        return this.courses.remove(course);
    }


}
