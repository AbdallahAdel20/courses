package com.example.courses.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

//    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
//    @JoinTable(name = "students_courses",
//            joinColumns = @JoinColumn(name = "course_id" , referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "student_id" , referencedColumnName = "id")
//    )

    @ManyToMany(mappedBy = "courses" , fetch = FetchType.LAZY)
//    @JsonBackReference
    @JsonIgnoreProperties("courses")
    private Set<Student> students = new HashSet<>();





//    public Course(String name, String description, Set<Student> students) {
//        this.name = name;
//        this.description = description;
//        this.students = students;
//    }
//
//    public Course() {
//    }
//
//    public Course(Long id, String name, String description, Set<Student> students) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.students = students;
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
//    public Set<Student> getStudents() {
//        return students;
//    }
//
//    public void setStudents(Set<Student> students) {
//        this.students = students;
//    }
}
