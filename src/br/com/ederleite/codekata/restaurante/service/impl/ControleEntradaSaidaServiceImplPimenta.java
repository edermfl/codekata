package br.com.ederleite.codekata.restaurante.service.impl;

import br.com.ederleite.codekata.restaurante.service.IControleEntradaSaidaService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author flap@flap.eti.br
 */
public class ControleEntradaSaidaServiceImplPimenta implements IControleEntradaSaidaService {

    public ControleEntradaSaidaServiceImplPimenta() {
	// TODO Auto-generated constructor stub
    }

    /**
     * Metodo criado apenas para exemplificar teste
     *
     * @param args
     */
    public static void main(String[] args) {
	ControleEntradaSaidaServiceImplPimenta impl = new ControleEntradaSaidaServiceImplPimenta();

	List<Integer> entrada = new ArrayList<Integer>();
	List<Integer> saida = new ArrayList<Integer>();
	for (int tempo = 1; tempo <= 5000; tempo++) {
	    entrada.add(-1 * tempo);
	    saida.add(2 * tempo + 1);
	}

	System.out.println(System.currentTimeMillis());
	Integer maxPessoas = impl.calcularMaximoPessoasSimultaneamenteNoRestaurante(entrada, saida);
	System.out.println("Max Pessoas: " + maxPessoas);
	System.out.println(System.currentTimeMillis());
    }

    @Override
    public Integer calcularMaximoPessoasSimultaneamenteNoRestaurante(
		    List<Integer> pListaEntradas, List<Integer> pListaSaidas)
		    throws IllegalArgumentException {

	/**
	 * Validacao de integridade das collections Entrada e Saida.
	 */
	if (pListaSaidas.size() != pListaEntradas.size()) {
	    throw new IllegalArgumentException(
			    "Dados inconsistentes! Quantidade de pessoas que entraram é diferente das que saíram.");
	}
	if (pListaSaidas.size() > 5000 || pListaEntradas.size() > 5000) {
	    throw new IllegalArgumentException("Dados inconsistentes! Quantidade de pessoas registradas passou de 5000!");
	}

	/**
	 * Array para armazenar a atividade em cada instante (range: 1 < E < S <= 15000)
	 * primeira posicao (zero) nao sera utilizada
	 * Valores esperados:
	 *  0 -> default, indica que ninguem entrou e nem saiu
	 *  1 -> entrou 1 pessoa
	 *  -1 -> saiu 1 pessoa
	 */
	int[] atividadeNoInstante = new int[15001];

	Iterator<Integer> iteratorSaida = pListaSaidas.iterator();

	/**
	 * atributos auxiliares no for
	 */
	Integer tempoEntrada;
	Integer tempoSaida;

	/**
	 *  Como pListaEntradas.size() == pListaEntradas.size(), pois foi validado, so precisamos fazer um for.
	 */
	for (Iterator<Integer> iteratorEntrada = pListaEntradas.iterator(); iteratorEntrada.hasNext(); ) {
	    tempoEntrada = (Integer) iteratorEntrada.next();
	    tempoSaida = (Integer) iteratorSaida.next();

	    atividadeNoInstante[tempoEntrada] = 1;
	    atividadeNoInstante[tempoSaida] = -1;
	}

	/**
	 * Inicializacao de atributo que armazena o resultado a ser retornado.
	 */
	int resultado = 0;

	/**
	 * Simula a entrada e saida em cada instance, contado e armazenando o maior
	 */
	int qtdPessoasAgora = 0;
	for (int instante = 1; instante < atividadeNoInstante.length; instante++) {
	    qtdPessoasAgora = qtdPessoasAgora + atividadeNoInstante[instante];
	    if (qtdPessoasAgora > resultado) {
		resultado = qtdPessoasAgora;
	    }
	}

	return Integer.valueOf(resultado);
    }

}
