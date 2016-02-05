package br.com.ederleite.example.codekata.encontreSequencia.service;

import br.com.ederleite.example.codekata.encontreSequencia.domain.model.PosicaoTO;

import java.util.ArrayList;
import java.util.List;

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
	if (!verificaStringValida(p)) {
	    throw new RuntimeException("p possui caracteres inválidos " + p + ", são válidos apenas os caracteres A, B, X, Y ");
	}
	if (!verificaStringValida(t)) {
	    throw new RuntimeException("t possui caracteres inválidos " + t + ", são válidos apenas os caracteres A, B, X, Y ");
	}

	posicao.getListaPosicoesDireta().addAll(procurarPosicoes(p, t));
	posicao.getListaPosicoesReversa().addAll(procurarPosicoes(inverterString(p), t));

	return posicao;
    }

    private String inverterString(String p) {
	return new StringBuffer(p).reverse().toString();
    }

    private List<Integer> procurarPosicoes(final String p, final String t) {
	List<Integer> listaPosicoes = new ArrayList<Integer>();
	int anterior = 0;
	if (p.equals(t)) {
	    listaPosicoes.add(1);
	}
	final String[] partes = t.split(p);
	for (String parte : partes) {
	    int posicaoAtual = anterior + parte.length() + 1;
	    if (t.length() >= posicaoAtual) {
		listaPosicoes.add(posicaoAtual);
		anterior += parte.length() + p.length();
	    }
	}
	return listaPosicoes;
    }

    private boolean verificaStringValida(final String pString) {
	return pString.matches("[ABXY]*");
    }

}
