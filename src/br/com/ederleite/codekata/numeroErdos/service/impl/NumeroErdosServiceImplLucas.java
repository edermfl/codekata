package br.com.ederleite.codekata.numeroErdos.service.impl;

import br.com.ederleite.codekata.numeroErdos.service.INumeroErdosService;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by las.
 */
public class NumeroErdosServiceImplLucas implements INumeroErdosService {

    @Override
    public Integer descobrirNumeroErdosDoAutor(final String pNomeAutor, final List<String> pAutoresArtigos)
		    throws IllegalArgumentException {
	validarNomeAutor(pNomeAutor);
	validarListaAutores(pAutoresArtigos);

	Map<String, Set<String>> autores = recuperarAutores(pAutoresArtigos);
	//        System.out.println(autores);

	Map<String, Integer> numeros = calcularNumerosDeErdos(autores);
	//        System.out.println(numeros);

	Integer numeroDeErdos = numeros.get(pNomeAutor);
	return (numeroDeErdos != null) ? numeroDeErdos : -1;
    }

    private Map<String, Integer> calcularNumerosDeErdos(Map<String, Set<String>> autores) {
	Map<String, Integer> numerosDeErdos = new HashMap<String, Integer>();

	Queue<Autor> queue = new LinkedBlockingQueue<Autor>();
	queue.add(new Autor("P. Erdos", 0));

	while (!queue.isEmpty()) {
	    Autor autorAtual = queue.poll();
	    if (!numerosDeErdos.containsKey(autorAtual.getNome())) {
		numerosDeErdos.put(autorAtual.getNome(), autorAtual.getDistancia());
	    }
	    for (String author : autores.get(autorAtual.getNome())) {
		if (!numerosDeErdos.containsKey(author)) {
		    queue.add(new Autor(author, autorAtual.getDistancia() + 1));
		}
	    }
	}

	return numerosDeErdos;
    }

    private List<String> limparNomeDosAutores(String nomes) {
	List<String> nomesAutores = new ArrayList<String>();
	for (String nome : nomes.split(",")) {
	    nomesAutores.add(nome.trim());
	}
	return nomesAutores;
    }

    private Map<String, Set<String>> recuperarAutores(List<String> pAutoresArtigos) {
	Map<String, Set<String>> autores = new HashMap<String, Set<String>>();

	for (String nomes : pAutoresArtigos) {
	    List<String> nomesAutores = limparNomeDosAutores(nomes);
	    validarQuantidadeDeAutoresDoArtigo(nomesAutores);

	    for (String nomeAutor : nomesAutores) {
		Set<String> outrosAutores = autores.get(nomeAutor);
		if (outrosAutores == null) {
		    outrosAutores = new LinkedHashSet<String>();
		    autores.put(nomeAutor, outrosAutores);
		}
		for (String coautor : nomesAutores) {
		    if (coautor.equals(nomeAutor)) {
			continue;
		    }
		    outrosAutores.add(coautor);
		}
	    }
	}
	return autores;
    }

    private void validarListaAutores(List<String> pAutoresArtigos) {
	if (pAutoresArtigos == null || pAutoresArtigos.isEmpty()) {
	    throw new IllegalArgumentException("Artigos devem ser informados  (1 à 100)");
	}

	if (pAutoresArtigos.size() < 1 || pAutoresArtigos.size() > 100) {
	    throw new IllegalArgumentException("Número de artigos:  1 à 100");
	}
    }

    private void validarNomeAutor(String pNomeAutor) {
	if (pNomeAutor == null || pNomeAutor.isEmpty()) {
	    throw new IllegalArgumentException("Nome do autor deve ser informado (1 a 15 caracteres)s");
	}

	if (pNomeAutor.length() > 15) {
	    throw new IllegalArgumentException("Nome do autor deve ter no máximo 15 caracteres");
	}
    }

    private void validarQuantidadeDeAutoresDoArtigo(List<String> pAutoresDoArtigo) {
	if (pAutoresDoArtigo.size() < 1 || pAutoresDoArtigo.size() > 10) {
	    throw new IllegalArgumentException("Número autores por artigo: 1 à 10");
	}
    }

    private static class Autor {
	private final Integer distancia;

	private final String nome;

	public Autor(String nome, Integer distancia) {
	    this.nome = nome;
	    this.distancia = distancia;
	}

	public Integer getDistancia() {
	    return distancia;
	}

	public String getNome() {
	    return nome;
	}
    }
}
