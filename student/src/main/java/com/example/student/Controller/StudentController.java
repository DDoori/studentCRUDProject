package com.example.student.Controller;

import com.example.student.domain.Student;
import com.example.student.dto.StudentCreateRequest;
import com.example.student.dto.StudentUpdateRequest;
import com.example.student.repository.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody StudentCreateRequest request) {
        Student student = new Student(request.getName(), request.getAge());
        return studentRepository.save(student);
    }

    @Transactional
    @PutMapping("/students/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody StudentUpdateRequest request
    ) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("학생 없음"));

        student.update(request.getName(), request.getAge());
        return student;
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}