package com.df.admin.controller;

import com.df.admin.dto.StudentDto;
import com.df.admin.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(StudentController.class)
class StudentControllerTest {

    private WebTestClient webTestClient;

    @MockBean
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(new StudentController(studentService)).build();
    }

    @Test
    void createStudent_ShouldReturnStudentDto() {
        StudentDto studentDto = StudentDto.builder()
                .id(1L)
                .name("Stephanie")
                .lastName("Alva")
                .age(32)
                .status("ACTIVE")
                .build();
        Mockito.when(studentService.createStudent(any(StudentDto.class))).thenReturn(Mono.just(studentDto));

        webTestClient.post()
                .uri("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(studentDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StudentDto.class)
                .isEqualTo(studentDto);
    }

    @Test
    void getStudents_ShouldReturnListOfStudents() {
        StudentDto student1 = StudentDto.builder()
                .id(1L)
                .name("Stephanie")
                .lastName("Alva")
                .age(32)
                .status("ACTIVE")
                .build();
        StudentDto student2 = StudentDto.builder()
                .id(2L)
                .name("Christian")
                .lastName("Alva")
                .age(28)
                .status("ACTIVE")
                .build();
        Mockito.when(studentService.getStudents()).thenReturn(Flux.just(student1, student2));

        webTestClient.get()
                .uri("/student")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StudentDto.class)
                .hasSize(2)
                .contains(student1, student2);
    }
}

