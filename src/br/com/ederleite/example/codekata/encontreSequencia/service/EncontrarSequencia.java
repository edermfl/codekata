package br.com.ederleite.example.codekata.encontreSequencia.service;

import br.com.ederleite.example.codekata.encontreSequencia.domain.model.PosicaoTO;

/**
 * Created by eml on 29/01/16.
 */
public class EncontrarSequencia implements IEncontrarSequencia {

    @Override
    public PosicaoTO encontrar(final String p, final String t) {
	final PosicaoTO posicao = new PosicaoTO();

	final int tamanhoT = t.length();
	if (p.length() > tamanhoT) {
	    throw new RuntimeException("p é maior que t.");
	}

	int anterior = 0;
	final String[] partes = t.split(p);
	for (String parte : partes) {
	    int posicaoAtual = anterior + parte.length() + 1;
	    if (tamanhoT >= posicaoAtual) {
		posicao.addPosicaoDireta(posicaoAtual);
		anterior += parte.length() + p.length();
	    }
	}

	anterior = 0;
	final String pInvertido = inverterString(p);
	final String[] partesInvertido = t.split(pInvertido);
	for (String parte : partesInvertido) {
	    int posicaoAtual = anterior + parte.length() + 1;
	    if (tamanhoT >= posicaoAtual) {
		posicao.addPosicaoReversa(posicaoAtual);
		anterior += parte.length() + p.length();
	    }
	}

	return posicao;
    }

    private String inverterString(String p) {
	return new StringBuffer(p).reverse().toString();
    }

}
