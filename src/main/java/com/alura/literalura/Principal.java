package com.alura.literalura;

import com.alura.literalura.dto.LivroDto;
import com.alura.literalura.dto.ResultadoApiDto;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;
import com.alura.literalura.service.LivroService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String  ENDERECO = "https://gutendex.com/books/?search=";
    private LivroService livroService;
    private AutorService autorService;

    public Principal(LivroService livroService, AutorService autorService) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    public void exibeMenu() {
        int menu = -1;
        while (menu !=8){
            System.out.println("""
                    -------Menu-------
                    1 - Busca de livro por título
                    2 - Listagem de todos os livros
                    3 - Lista de autores
                    4 - Listar autores vivos em determinado ano
                    """);
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
                }case 3:{
                    listarAutores();
                    break;
                }case 4:{
                    listarAutoresVivosPorAno();
                    break;
                }
                default:
                    System.out.println("Opção invalida.");
            }
        }



    }

    private void listarAutoresVivosPorAno() {
        System.out.print("Listar autores vivos a partir de que ano?");
        var ano = teclado.nextLong();
        teclado.nextLine();
        List<Autor> autoresEncontrados =autorService.listarAutoresVivosPorAno(ano);
        if (!autoresEncontrados.isEmpty()){
            autoresEncontrados.forEach(autor -> System.out.println("Nome do autor: " + autor.getNome() +
                    "\nAno de Nascimento do autor: " + autor.getAnoNascimento() +
                    "\nAno de Falecimento do autor: " + autor.getAnoFalecimento()));
        }else {
            System.out.println("Nenhum autor com  ano de falecimento " + ano + "encontrado");
        }
    }

    private void listarAutores() {
        System.out.println("------Autores------");
        List<Autor> autores = autorService.listarAutores();
        autores.forEach(autor -> {
            System.out.println(
                    "Nome do Autor: " + autor.getNome() +
                            "\nAno de nascimento: " + autor.getAnoNascimento() +
                            "\nAno de falecimento: " + autor.getAnoFalecimento()
            );
            autor.getLivros().forEach(livro -> System.out.println("Titulo: [" + livro.getTitulo() + "]"));
            System.out.println("*****************");
        });
    }


    private void listarLivros() {
        System.out.println("------Livros------");
        List<Livro> livros = livroService.listarLivros();
        livros.forEach(l -> System.out.println("Titulo: "+ l.getTitulo() +
                "\nIdiomas:" + l.getIdiomas() +
                "\nTotal de downloads: " + l.getTotalDownloads() +
                "\nNome do Autor: " + l.getAutor().getNome() +
                "\n*****************"));
    }

    private void buscarLivro() {
        System.out.print("Busca de livro por titulo: ");
        var nomeLivro = teclado.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        ResultadoApiDto dados = conversor.obterDados(json, ResultadoApiDto.class);


        if (dados.livros() != null && !dados.livros().isEmpty()) {
            LivroDto livroBuscado = dados.livros().get(0);
            Livro livro = new Livro(livroBuscado);
            if (livroService.validarLivro(livro.getTitulo()).isPresent()){
                System.out.println("Este livro já está cadastrado no banco, tente listar os livros.");
            }else {
                System.out.println("Livro encontrado!");
                System.out.println("-------Livro-------");
                System.out.println("Titulo: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor().getNome());
                System.out.println("Idioma: " + livro.getIdiomas());
                System.out.println("Total de downloads: " + livro.getTotalDownloads());
                livroService.salvarLivro(livro);
                System.out.println("Livro salvo no banco");
            }
        }else {
            System.out.println("Nenhum livro encontrado.");
        }

    }
}
