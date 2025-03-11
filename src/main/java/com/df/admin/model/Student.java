package com.df.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("student")
public class Student {
    @Column("id")
    private Long idStudent;
    private String firstname;
    private String lastname;
    private boolean active;
    private int age;
}
