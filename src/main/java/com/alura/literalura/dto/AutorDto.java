package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDto(@JsonAlias("name") String nome,
                       @JsonAlias("birth_year")Long anoNascimento,
                       @JsonAlias("death_year")Long anoFalecimento) {
}
