package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    LivroRepository livroRepository;
    public void salvarLivro(Livro livro) {
        livroRepository.save(livro);
    }

    public Optional<Livro> validarLivro(String titulo) {
        return livroRepository.findByTitulo(titulo);
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }


    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdiomas(idioma);
    }
}
