package br.com.ederleite.codekata.domino.service;

import br.com.ederleite.codekata.domino.domain.model.PecaDomino;
import br.com.ederleite.codekata.domino.domain.model.Tabuleiro;

import java.util.List;

/**
 * Created by eml on 24/03/16.
 */
public interface IJogoDominoService {

    /**
     * CodeKata #4: Jogo de Dominó
     * Método responsável resolver a sequência das peças de Domino.
     * Será informado uma lista de peças de dominó, e o retorno esperado é uma instância de tabuleiro,
     * contendo uma lista ordenada de peças devidamente encaixadas e uma lista de peças que sobraram,
     * ou seja, não foi possível o uso.
     * Caso a alguma regra de restrição seja infringida então lançar IllegalArgumentException.
     *
     * @param pPecas
     * @throws IllegalArgumentException
     */
    public Tabuleiro jogar(List<PecaDomino> pPecas) throws IllegalArgumentException;
}
