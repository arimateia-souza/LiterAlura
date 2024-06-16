package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDto(@JsonAlias("title") String titulo,
                       @JsonAlias("authors")List<AutorDto> autores,
                       @JsonAlias("languages")List<String> idioma,
                       @JsonAlias("download_count")Long totalDownloads) {
}
