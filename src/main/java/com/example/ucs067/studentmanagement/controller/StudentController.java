package com.example.ucs067.studentmanagement.controller;

import com.example.ucs067.studentmanagement.Student;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>(
            List.of(
                    new Student(1, "Rahul", "rahul@example.com"),
                    new Student(2, "Priya", "priya@example.com"),
                    new Student(3, "Amit", "amit@example.com")
            )
    );

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody Student student) {

        // Generate a new ID
        int newId = students.stream()
                .mapToInt(Student::getId)
                .max()
                .orElse(0) + 1;

        student.setId(newId);
        students.add(student);

        return student;
    }

    // READ - Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    // READ - Get student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {

        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable int id,
            @RequestBody Student updatedStudent
    ) {

        for (Student student : students) {

            if (student.getId() == id) {

                student.setName(updatedStudent.getName());
                student.setEmail(updatedStudent.getEmail());

                return student;
            }
        }

        return null;
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable int id) {

        students.removeIf(student -> student.getId() == id);
    }
}