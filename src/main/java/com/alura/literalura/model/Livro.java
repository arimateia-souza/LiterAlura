package com.alura.literalura.model;

import com.alura.literalura.dto.LivroDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "livros")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
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

    @Override
    public String toString() {
        return  "Titulo=" + titulo  +
                ", Autor=" + autor +
                ", Idiomas=" + idiomas +
                ", totalDownloads=" + totalDownloads;
    }
}
