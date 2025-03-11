package com.df.admin.service.impl;

import com.df.admin.dto.StudentDto;
import com.df.admin.exception.ErrorCode;
import com.df.admin.exception.StudentException;
import com.df.admin.model.Student;
import com.df.admin.repository.StudentRepository;
import com.df.admin.service.StudentService;
import com.df.admin.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public Mono<StudentDto> createStudent(StudentDto studentDto) {
        return studentRepository.findByIdStudent(studentDto.getId())
                .flatMap(existingStudent ->
                        Mono.error(new StudentException(Constants.EXIST_STUDENT, ErrorCode.BAD_REQUEST)))
                .then(studentRepository.save(this.buildStudent(studentDto))
                        .map(this::buildStudentDto));
    }

    @Override
    public Flux<StudentDto> getStudents() {
        return studentRepository.findByActive(true)
                .switchIfEmpty(Mono.error(new StudentException(Constants.NO_DATA, ErrorCode.NO_CONTENT)))
                .map(this::buildStudentDto);
    }

    private Student buildStudent(StudentDto studentDto) {
        return Student.builder()
                .idStudent(studentDto.getId())
                .firstname(studentDto.getName())
                .lastname(studentDto.getLastName())
                .age(studentDto.getAge())
                .active(true)
                .build();
    }

    private StudentDto buildStudentDto(Student student) {
        return StudentDto.builder()
                .id(student.getIdStudent())
                .name(student.getFirstname())
                .lastName(student.getLastname())
                .age(student.getAge())
                .status(student.isActive() ? Constants.ACTIVE : Constants.INACTIVE)
                .build();
    }
}
