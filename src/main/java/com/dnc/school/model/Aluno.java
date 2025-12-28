package com.dnc.school.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_alunos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Aluno extends Usuario {

    @Column(unique = true, nullable = false)
    private String matricula;
}
