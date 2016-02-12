package br.com.ederleite.codekata.encontreSequencia.service.impl;

import br.com.ederleite.codekata.encontreSequencia.domain.model.PosicaoTO;
import br.com.ederleite.codekata.encontreSequencia.service.IEncontrarSequencia;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fcs on 08/02/16.
 */
public class EncontrarSequenciaFernando implements IEncontrarSequencia {

    @Override public PosicaoTO encontrar(final String p, final String t) {
	PosicaoTO retorno = new PosicaoTO();
	Pattern pattern = Pattern.compile(p);
	Matcher m = pattern.matcher(t);
	while (m.find()) {
	    retorno.addPosicaoDireta((m.end() - p.length()) + 1);
	}
	pattern = Pattern.compile(new StringBuffer(p).reverse().toString());
	m = pattern.matcher(t);
	while (m.find()) {
	    retorno.addPosicaoReversa((m.end() - p.length()) + 1);
	}
	return retorno;
    }
}