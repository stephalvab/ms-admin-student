package com.df.admin.service;

import com.df.admin.dto.StudentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {
    Mono<StudentDto> createStudent(StudentDto studentDto);

    Flux<StudentDto> getStudents();
}
