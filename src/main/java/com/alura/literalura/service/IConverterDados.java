package com.alura.literalura.service;

public interface IConverterDados {
    <T> T obterDados(String json, Class<T> classe);
}
