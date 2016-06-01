package br.com.ederleite.codekata.numeroErdos.service.impl;

import br.com.ederleite.codekata.numeroErdos.service.INumeroErdosService;

import java.util.*;

public class NumeroErdosServiceImplPimenta implements INumeroErdosService {

	
	final boolean IS_CASE_SENSITIVE = false;
	final String ERDOS = (IS_CASE_SENSITIVE) ? "P.Erdos" : "P.ERDOS";
	final int INFINITO = 10000;
	
	List<String> listaAmigoQueBusca = new ArrayList<String>();
	Map<String,List<String>> mapaAutores = new HashMap<String,List<String>>();
	
	
	public NumeroErdosServiceImplPimenta(){
		mapaAutores =  new HashMap<String,List<String>>();
		listaAmigoQueBusca = new ArrayList<String>();
	}
	
	/**
	 * Adiciona referencia entre todos os autores de um artigo.
	 * @param pAutoresDeUmArtigo
	 */
	private void popularMapa(List<String> pAutoresDeUmArtigo){
		
		for (Iterator<String> iterator = pAutoresDeUmArtigo.iterator(); iterator.hasNext();) {
						
			String autor = validarAutor((String) iterator.next());
			
			if (!mapaAutores.containsKey(autor)){
				mapaAutores.put(autor,new ArrayList<String>());
			}
			for (Iterator<String> iterator2 = pAutoresDeUmArtigo.iterator(); iterator2.hasNext();) {
				String autor2 = validarAutor((String) iterator2.next());
				if (!autor.equals(autor2)){
					if (!mapaAutores.get(autor).contains(autor2)){
						mapaAutores.get(autor).add(autor2);
					}
				}
			}
			
			
		}
		
		
	}
	
	/**
	 * Valida formato de nome de um autor: [LETRA_PRIMEIRO_NOME].[SOBRENOME]
	 * @param pAutor
	 * @return
	 */
	
	private String validarAutor(String pAutor) {
		if (pAutor.length() > 15){
			throw new IllegalArgumentException( "Violou restricao: Nome do autor deve ter no maximo 15 caracteres");
		}
		
		// Considero que "P. Erdos" eh o mesmo que "P.Erdos". Ignorando o espaco
		String retorno = pAutor.trim().replace(" ", "");
		if (!IS_CASE_SENSITIVE){
			retorno = retorno.toUpperCase();
		}
		
		if ( retorno.charAt(1) != ".".charAt(0)){
			throw new IllegalArgumentException("Violou premissa: Cada autor tem seu nome inicial abreviado (uma letra mais .) seguindo do seu último nome");
		}
		
		return retorno;
	}

	private void inicializarListaRelacionamento(List<String> pAutoresArtigos ) throws IllegalArgumentException {
		
		
		for (Iterator<String> iterator = pAutoresArtigos.iterator(); iterator.hasNext();) {
			String autores = (String) iterator.next();
			List<String> listAutoresDesteArtigo = Arrays.asList( autores.split(",") );
			
			if (listAutoresDesteArtigo.size() > 10){
				throw new IllegalArgumentException("Premissa/Restricao violada: Cada artigo pode possuir de 1 a 10 autores");
			}
			
			popularMapa(listAutoresDesteArtigo);
		}
		
		
	}
	
	int interacao = 0;
	
	/**
	 * 
	 * @param pNomeAutor	
	 */
	private int getNumeroErdos(String pNomeAutor){
		int retorno = INFINITO;
		
		try {

			int menorErdos = INFINITO;

			if ( ERDOS.equals(pNomeAutor) ){
				retorno = 0;
			}
			else if (mapaAutores.get(pNomeAutor).contains(ERDOS)){
				retorno = 1;
			}
			else{

				for (Iterator<String> iterator = mapaAutores.get(pNomeAutor).iterator(); iterator.hasNext();) {
					String autorAmigo = (String) iterator.next();
					if (! listaAmigoQueBusca.contains(autorAmigo)){
						listaAmigoQueBusca.add(pNomeAutor);
						int numeroErdosDoAmigo = getNumeroErdos(autorAmigo);
						int meuNumeroErdos = numeroErdosDoAmigo + 1;
						if (meuNumeroErdos < menorErdos){
							menorErdos = meuNumeroErdos;
						}
						interacao++;
					}
				}
				retorno = menorErdos;
			}
		} catch (StackOverflowError e) {
			System.out.println("###############################");
			System.out.println("      MEMORIA INSUFICIENTE     ");
			System.out.println("###############################");
			System.out.println("Interacao" + interacao);
			
			new Exception("Memoria insuficiente para executar este caso.");
			
		}	
		return retorno;

	}
	
	@Override
	public Integer descobrirNumeroErdosDoAutor(String pNomeAutor,
			List<String> pAutoresArtigos)  throws IllegalArgumentException {
		mapaAutores =  new HashMap<String,List<String>>();
		listaAmigoQueBusca = new ArrayList<String>();
		
		pNomeAutor = validarAutor(pNomeAutor);
		
		// Validacao de qtd de artigos
		if (pAutoresArtigos.size() < 1 || pAutoresArtigos.size() > 100){
			throw new IllegalArgumentException("Restricao violada: Numero de artigos:  1 a 100");
		}
		
		// valida qtd de autores por artigo aqui na inicializacao
		inicializarListaRelacionamento(pAutoresArtigos);
		
		// Validacao se autor escreveu artigo
		if (!mapaAutores.containsKey(pNomeAutor)){
			throw new IllegalArgumentException("Autor informado não escreveu nenhum artigo. Violou premissa: Dada a lista de autores, sera solicitado o numero de Erdos de um autor da lista");
		}
		
		int numeroErdos = getNumeroErdos(pNomeAutor);
		
		if (INFINITO == numeroErdos){
			numeroErdos = -1;
		}
		
		//TODO: remover debug
				System.out.println(mapaAutores.toString());
				
		return Integer.valueOf(numeroErdos);
	}

	

	public static void main(String[] args) {
		NumeroErdosServiceImplPimenta nErdos = new NumeroErdosServiceImplPimenta();
		List<String> lista = new ArrayList<String>();
		lista.add("P. Erdos, J.1");
		lista.add("J.1,J.2");
		lista.add("J.2,J.3");
		lista.add("J.3,J.4");
		lista.add("J.4,J.5");
		lista.add("J.5,J.6");
		lista.add("J.6,J.7");
		lista.add("J.7,J.8");
		lista.add("J.8,J.9");
		lista.add("J.9,J.10");
		lista.add("J.11,J.12");
		lista.add("J.12,J.13");
		
		
		
		
		System.out.println(nErdos.descobrirNumeroErdosDoAutor("P.ERDOS", lista) );

	}

}
