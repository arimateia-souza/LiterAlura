package com.alura.literalura.model;

import com.alura.literalura.dto.LivroDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "livros")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne(cascade=CascadeType.PERSIST)
    private Autor autor;
    private String idiomas;
    private Long totalDownloads;


    public Livro(LivroDto livroBuscado) {
        this.titulo = livroBuscado.titulo();
        this.idiomas = livroBuscado.idioma().get(0);
        this.autor = new Autor(livroBuscado.autores().get(0));
        this.totalDownloads = livroBuscado.totalDownloads();

    }
}
