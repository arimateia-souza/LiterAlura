package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AutorRepository extends JpaRepository<Autor,Long> {
    List<Autor> findAutorByAnoFalecimentoAfter(Long ano);

    @Query("SELECT a from Autor a WHERE LOWER(a.nome) ILIKE %:nome%")
    List<Autor> findAutorByNome(String nome);
}
