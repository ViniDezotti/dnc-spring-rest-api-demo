package com.dnc.school.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
public class Endereco {

    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
