package br.com.ederleite.codekata.numeroErdos.service;

import java.util.List;

/**
 * Created by eder on 08/05/2016.
 */
public interface INumeroErdosService {

    /**
     * M�todo respons�vel por descobrir o n�mero de Erdos de um dado autor.
     * Esse n�mero dever� ser descoberto, considerando a lista de autores de uma dada lista de artigos.
     * Exemplo de lista de autores de artigos:
     * - P. Erdos, A. Selberg.
     * - P. Erdos, J. Silva, M. Souza.
     * - M. Souza, A. Selberg, A. Oliveira.
     * - J. Ninguem, M. Ninguem.
     * - P. Duarte, A. Oliveira.
     * Como retorno, � esperado um n�mero inteiro representando o N�mero de Erdos do autor.
     * @param pNomeAutor
     * @param pAutoresArtigos
     * @return
     */
    public Integer descobrirNumeroErdosDoAutor(String pNomeAutor, List<String> pAutoresArtigos);
}
