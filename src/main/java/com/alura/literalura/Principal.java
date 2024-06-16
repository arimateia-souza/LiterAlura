package com.alura.literalura;

import com.alura.literalura.dto.LivroDto;
import com.alura.literalura.dto.ResultadoApiDto;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String  ENDERECO = "https://gutendex.com/books/?search=";
    private LivroRepository livroRepository;

    public Principal(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void exibeMenu() {
        int menu = -1;
        while (menu !=8){
            System.out.println("""
                    -------Menu-------
                    1 - Buscar um Livro""");
            System.out.print("Escolha uma opção: ");
            menu = teclado.nextInt();
            teclado.nextLine();
            switch (menu){
                case 1:{
                    buscarLivro();
                    break;
                }case 2:{
                    listarLivros();
                    break;
                }
                default:
                    System.out.println("Opção invalida.");
            }
        }



    }

    private void listarLivros() {
    }

    private void buscarLivro() {
        System.out.print("Busca de livro por titulo: ");
        var nomeLivro = teclado.nextLine();
        var a = ENDERECO + nomeLivro.replace(" ", "+");
        var json = consumo.obterDados(a);
        ResultadoApiDto dados = conversor.obterDados(json, ResultadoApiDto.class);
        if (dados.livros() != null && !dados.livros().isEmpty()) {
            LivroDto livroBuscado = dados.livros().get(0);
            Livro livro = new Livro(livroBuscado);
            Autor autor = new Autor(livroBuscado.autores().get(0));
            System.out.println(livro);

            livroRepository.save(livro);
        } else {
            System.out.println("Nenhum livro encontrado.");
        }

    }
}
