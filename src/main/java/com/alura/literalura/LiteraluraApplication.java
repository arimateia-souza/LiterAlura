package com.alura.literalura;

import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    LivroService livroService;
    @Autowired
    AutorService autorService;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(livroService,autorService);
        principal.exibeMenu();
    }
}
