package br.com.ederleite.codekata.encontreSequencia.service;

import br.com.ederleite.codekata.encontreSequencia.domain.model.PosicaoTO;

/**
 * Created by eml on 29/01/16.
 */
public interface IEncontrarSequencia {
    public PosicaoTO encontrar(final String p, final String t);
}
