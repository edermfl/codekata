package br.com.ederleite.codekata.numeroErdos.service.impl;

import br.com.ederleite.codekata.numeroErdos.service.INumeroErdosService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by eder on 08/05/2016.
 */
public class NumeroErdosServiceImplJunior implements INumeroErdosService {

    @Override
    public Integer descobrirNumeroErdosDoAutor(final String pNomeAutor, final List<String> pAutoresArtigos)
		    throws IllegalArgumentException {
	int numErdos = 0;

	final String erdos = "P. Erdos";

	List<String> autoresQueJaEscreveramComErdos = new ArrayList<String>();
	List<String> autoresQueNaoEscreveramComErdos = new ArrayList<String>();
	List<String> autoresQueNaoEQueJaEscreveramComErdos = new ArrayList<String>();

	if (pNomeAutor.equals(erdos)) {
	    return numErdos;
	}

	for (int i = 0; i < pAutoresArtigos.size(); i++) {

	    final String[] autoresArtigo = pAutoresArtigos.get(i).split(",");

	    final List<String> autores = Arrays.asList(autoresArtigo);

	    // Verifico se � AUTOR � Erdos e j� add na lista e continuo
	    if (autores.contains(erdos)) {

		for (String actor : autores) {
		    autoresQueJaEscreveramComErdos.add(actor.trim());

		}
	    } else {

		for (String actor : autores) {

		    if (autoresQueJaEscreveramComErdos.contains(actor.trim()) || pNomeAutor.equals(actor.trim())) {

			autoresQueNaoEQueJaEscreveramComErdos.add(actor.trim());

		    } else {
			autoresQueNaoEscreveramComErdos.add(actor.trim());
		    }

		}

	    }

	}

	if (autoresQueJaEscreveramComErdos.contains(pNomeAutor)) {
	    numErdos = 1;
	} else if (autoresQueNaoEQueJaEscreveramComErdos.contains(pNomeAutor)) {
	    numErdos = 2;
	} else {
	    numErdos = -1;
	}

	return numErdos;
    }
}
