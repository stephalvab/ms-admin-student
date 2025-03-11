package com.df.admin.repository;

import com.df.admin.model.Student;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StudentRepository extends R2dbcRepository<Student, Long> {
    Flux<Student> findByActive(Boolean active);
    Mono<Student> findByIdStudent(Long idStudent);
}

