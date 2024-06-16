package com.alura.literalura.model;

import com.alura.literalura.dto.AutorDto;
import com.alura.literalura.dto.LivroDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Long anoNascimento;
    private Long anoFalecimento;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "autor")
    private List<Livro> livros = new ArrayList<>();

    public Autor(AutorDto autorDto) {
        this.nome = autorDto.nome();
        this.anoFalecimento = autorDto.anoFalecimento();
        this.anoNascimento = autorDto.anoNascimento();
    }
}
