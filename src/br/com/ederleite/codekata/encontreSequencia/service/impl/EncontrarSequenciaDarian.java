package br.com.ederleite.codekata.encontreSequencia.service.impl;

import br.com.ederleite.codekata.encontreSequencia.domain.model.PosicaoTO;
import br.com.ederleite.codekata.encontreSequencia.service.IEncontrarSequencia;

/**
 * @author darian.beluzzo
 * @version 1.0
 * @since 04/02/16
 */
public class EncontrarSequenciaDarian implements IEncontrarSequencia {

    /**
     * @param p
     * @param t
     * @return
     */
    public PosicaoTO encontrar(String p, String t) {
	final PosicaoTO posicaoTO = new PosicaoTO();
	final String tReverse = new StringBuilder(t).reverse().toString();

	for (int i = 0; i < t.length(); i++) {
	    final int endIndex = i + p.length() > t.length() ? t.length() : i + p.length();
	    if (p.equals(t.substring(i, endIndex)))
		posicaoTO.addPosicaoDireta(i+1);
	    if (p.equals(tReverse.substring(i, endIndex)))
		posicaoTO.addPosicaoReversa(i+1);
	}
	return posicaoTO;
    }
}
