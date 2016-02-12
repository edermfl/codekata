package br.com.ederleite.codekata.encontreSequencia.service.impl;

import br.com.ederleite.codekata.encontreSequencia.domain.model.PosicaoTO;
import br.com.ederleite.codekata.encontreSequencia.service.IEncontrarSequencia;

public class EncontrarSequenciaDarianV2 implements IEncontrarSequencia {

    /**
     * AXYBABYXAAABXBBYX
     *
     * @param p
     * @param t
     * @return
     */
    public PosicaoTO encontrar(String p, String t) {
	final PosicaoTO posicaoTO = new PosicaoTO();
        final String pReverse = new StringBuilder(p).reverse().toString();

        for (int i = 0; i < t.length(); i++) {
            final int endIndex = i + p.length() > t.length() ? t.length() : i + p.length();
            final String substring = t.substring(i, endIndex);
	    if (p.equals(substring)) {
		posicaoTO.addPosicaoDireta(i+1);
	    }
	    if (pReverse.equals(substring)) {
		posicaoTO.addPosicaoReversa(i+1);
	    }
        }
        return posicaoTO;
    }
}