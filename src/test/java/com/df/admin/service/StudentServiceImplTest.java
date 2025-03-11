package com.df.admin.service;

import com.df.admin.dto.StudentDto;
import com.df.admin.exception.StudentException;
import com.df.admin.model.Student;
import com.df.admin.repository.StudentRepository;
import com.df.admin.service.impl.StudentServiceImpl;
import com.df.admin.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStudent_ShouldReturnStudentDto_WhenStudentDoesNotExist() {
        StudentDto studentDto = StudentDto.builder()
                .id(1L)
                .name("Stephanie")
                .lastName("Alva")
                .age(32)
                .build();
        Student student = Student.builder()
                .idStudent(1L)
                .firstname("Stephanie")
                .lastname("Alva")
                .age(32)
                .active(true)
                .build();

        when(studentRepository.findByIdStudent(studentDto.getId())).thenReturn(Mono.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(Mono.just(student));

        StepVerifier.create(studentService.createStudent(studentDto))
                .expectNextMatches(savedStudent -> savedStudent.getId().equals(1L) && savedStudent.getName().equals("Stephanie"))
                .verifyComplete();
    }

    @Test
    void createStudent_ShouldThrowException_WhenStudentExists() {
        StudentDto studentDto = StudentDto.builder()
                .id(1L)
                .name("Stephanie")
                .lastName("Alva")
                .age(32)
                .build();
        Student student = Student.builder()
                .idStudent(1L)
                .firstname("Stephanie")
                .lastname("Alva")
                .age(32)
                .active(true)
                .build();
        when(studentRepository.findByIdStudent(studentDto.getId())).thenReturn(Mono.just(student));
        when(studentRepository.save(any(Student.class))).thenReturn(Mono.just(student));
        StepVerifier.create(studentService.createStudent(studentDto))
                .expectErrorMatches(throwable -> throwable instanceof StudentException &&
                        throwable.getMessage().equals(Constants.EXIST_STUDENT))
                .verify();
    }

    @Test
    void getStudents_ShouldReturnFluxOfStudents() {
        Student student1 = Student.builder()
                .idStudent(1L)
                .firstname("Stephanie")
                .lastname("Alva")
                .age(32)
                .active(true)
                .build();
        Student student2 = Student.builder()
                .idStudent(2L)
                .firstname("Christian")
                .lastname("Alva")
                .age(28)
                .active(true)
                .build();

        when(studentRepository.findByActive(true)).thenReturn(Flux.just(student1, student2));

        StepVerifier.create(studentService.getStudents())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void getStudents_ShouldThrowException_WhenNoDataAvailable() {
        when(studentRepository.findByActive(true)).thenReturn(Flux.empty());

        StepVerifier.create(studentService.getStudents())
                .expectErrorMatches(throwable -> throwable instanceof StudentException &&
                        throwable.getMessage().equals(Constants.NO_DATA))
                .verify();
    }
}
