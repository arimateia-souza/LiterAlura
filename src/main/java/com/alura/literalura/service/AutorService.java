package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    AutorRepository autorRepository;


    public List<Autor> listarAutores(){
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosApartirAno(Long ano) {
        return autorRepository.findAutorByAnoFalecimentoAfter(ano);
    }


    public List<Autor> buscarAutorPorNome(String autorNome) {
        return autorRepository.findAutorByNome(autorNome);
    }
}
