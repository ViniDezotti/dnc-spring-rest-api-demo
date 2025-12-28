package com.dnc.school.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_professores")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Professor extends Usuario {

    @Column(nullable = false)
    private String departamento;
}


