package br.com.ederleite.codekata.numeroErdos.service;

import java.util.List;

/**
 * Created by eder on 08/05/2016.
 */
public interface INumeroErdosService {

    /**
     * Método responsável por descobrir o número de Erdos de um dado autor.
     * Esse número deverá ser descoberto, considerando a lista de autores de uma dada lista de artigos.
     * Exemplo de lista de autores de artigos:
     * - P. Erdos, A. Selberg.
     * - P. Erdos, J. Silva, M. Souza.
     * - M. Souza, A. Selberg, A. Oliveira.
     * - J. Ninguem, M. Ninguem.
     * - P. Duarte, A. Oliveira.
     * @param pNomeAutor
     * @param pAutoresArtigos
     * @return é esperado um número inteiro representando o Número de Erdos do autor.
     */
    public Integer descobrirNumeroErdosDoAutor(String pNomeAutor, List<String> pAutoresArtigos) throws IllegalArgumentException;
}
