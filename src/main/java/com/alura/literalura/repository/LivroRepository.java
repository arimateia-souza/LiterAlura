package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Long> {
    Optional<Livro> findByTitulo(String titulo);

    List<Livro> findByIdiomas(String idioma);
}
