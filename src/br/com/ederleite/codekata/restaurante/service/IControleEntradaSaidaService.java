package br.com.ederleite.codekata.restaurante.service;

import java.util.List;

/**
 * Created by eml on 02/03/16.
 */
public interface IControleEntradaSaidaService {
    /**
     * Escreva um programa que, dado duas collections de inteiros E e S, ambos de comprimento igual a N,
     * calcula o número máximo de pessoas que estão presentes ao mesmo tempo dentro do restaurante.
     * Cada conjunto de E[i] e S[i], corresponde a entrada e saída de uma pessoa no restaurante.
     *
     * @param pListaEntradas correspontende a collection E,
     * @param pListaSaidas
     * @return número máximo de pessoas simultaneamente no restaurante
     */
    public Integer calcularMaximoPessoasSimultaneamenteNoRestaurante(List<Integer> pListaEntradas, List<Integer> pListaSaidas);
}
