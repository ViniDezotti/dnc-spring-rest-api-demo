package com.dnc.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViaCepResponse {

    private String cep;

    private String logradouro;

    private String bairro;

    @JsonProperty("localidade")
    private String cidade;

    @JsonProperty("uf")
    private String estado;
}
