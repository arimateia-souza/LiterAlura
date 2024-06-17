package com.alura.literalura;

import com.alura.literalura.dto.LivroDto;
import com.alura.literalura.dto.ResultadoApiDto;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;
import com.alura.literalura.service.LivroService;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final LivroService livroService;
    private final AutorService autorService;

    public Principal(LivroService livroService, AutorService autorService) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    public void exibeMenu() {
        int menu = -1;
        while (menu !=0){
            System.out.println("""
                    -------Menu-------
                    1 - Busca de livro por título
                    2 - Listagem de todos os livros
                    3 - Lista de autores
                    4 - Listar autores vivos em determinado ano
                    5 - Exibir a quantidade de livros em um determinado idioma
                    6 - Buscar autor por nome
                    0 - Sair
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
                    listarAutoresVivosApartirAno();
                    break;
                }case 5:{
                    listarLivrosPorIdioma();
                    break;
                }case 6:{
                    buscarAutorPorNome();
                    break;
                }
                default:
                    System.out.println("Opção invalida.");
            }
        }
    }

    private void buscarAutorPorNome() {
        System.out.println("Digite o nome do autor que deseja buscar: ");
        var autorNome = teclado.nextLine();
        List<Autor> autorsEncontrado = autorService.buscarAutorPorNome(autorNome);
        autorsEncontrado.forEach(autor -> System.out.println(
                "Autor(es) encontrado(s):\n" +
                autor.getNome()));
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                pt - portugues
                en - ingles
                es - espanhol
                fr - frances
                Escolha o idioma para buscar:
                """);
        var idioma = teclado.nextLine();
        List<Livro> livrosEcontrados = livroService.listarLivrosPorIdioma(idioma);
        if (!livrosEcontrados.isEmpty()){
            livrosEcontrados.forEach(livro -> System.out.println("Titulo: "+ livro.getTitulo() +
                    "\nIdiomas:" + livro.getIdiomas() +
                    "\nTotal de downloads: " + livro.getTotalDownloads() +
                    "\nNome do Autor: " + livro.getAutor().getNome() +
                    "\n*****************"));

        }else{
            System.out.println("Nenhum livro com este idioma encontrado.");
        }
    }

    private void listarAutoresVivosApartirAno() {
        System.out.print("Listar autores vivos a partir de que ano?");
        var ano = teclado.nextLong();
        teclado.nextLine();
        List<Autor> autoresEncontrados =autorService.listarAutoresVivosApartirAno(ano);
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
        String ENDERECO = "https://gutendex.com/books/?search=";
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
        teclado.close();

    }

}
