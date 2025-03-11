package com.df.admin.controller;

import com.df.admin.dto.StudentDto;
import com.df.admin.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @PostMapping()
    public Mono<ResponseEntity<StudentDto>> createStudent(@Valid @RequestBody StudentDto studentDto) {
        return studentService.createStudent(studentDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping()
    public Flux<StudentDto> getStudents() {
        return studentService.getStudents();
    }
}

