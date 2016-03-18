package br.com.ederleite.codekata.restaurante.service.impl;

import br.com.ederleite.codekata.restaurante.service.IControleEntradaSaidaService;

import java.util.List;

/**
 * https://edermfl.wordpress.com/2016/03/02/codekata-3-restaurante/
 * <p/>
 * Created by eml on 02/03/16.
 */
public class ControleEntradaSaidaServiceImplLucas implements IControleEntradaSaidaService {

    @Override
    public Integer calcularMaximoPessoasSimultaneamenteNoRestaurante(final List<Integer> pListaEntradas,
		    final List<Integer> pListaSaidas) {

	verificarNotNull(pListaEntradas, pListaSaidas);
	verificarTamanhoListas(pListaEntradas, pListaSaidas);
	verificarEntradaCrescente(pListaEntradas);
	verificarEntradaDiferenteDaSaida(pListaEntradas, pListaSaidas);

	int qtdeMaximaSimultanea = calcularQuantidadeMaximaSimultanea(pListaEntradas, pListaSaidas);
	return qtdeMaximaSimultanea;
    }

    private int calcularQuantidadeMaximaSimultanea(List<Integer> pListaEntradas, List<Integer> pListaSaidas) {
	/**
	 * armazena a quantidade de pessoas no restaurante no mesmo instante
	 *
	 * controle[<tempo decorrido da abertura> - 1] = <qtde de pessoas>
	 *
	 * ex.:
	 * controle[68] == 1
	 * controle[168] == 10
	 * controle[3000] == 0
	 */
	short[] controle = new short[15000];

	for (int i = 0; i < pListaEntradas.size(); i++) {
	    int entrada = pListaEntradas.get(i); // index do array == <tempo decorrido da abertura> - 1
	    int saida = pListaSaidas.get(i);

	    verificarLimiteInstante(entrada);
	    verificarLimiteInstante(saida);
	    verificarEntradaMenorQueSaida(entrada, saida);

	    for (int j = entrada; j < saida; j++) {
		controle[j] += 1;
	    }
	}

	int qtdeMaxima = 0;
	for (int i = 0; i < controle.length; i++) {
	    if (qtdeMaxima < controle[i]) {
		qtdeMaxima = controle[i];
	    }
	}

	return qtdeMaxima;
    }

    private void verificarEntradaCrescente(List<Integer> pListaEntradas) {
	// E[i] < E[i+i], para 1 ≤ i < N

	int size = pListaEntradas.size() - 1;
	for (int i = 0; i < size; i++) {
	    if (i < size) {
		if (pListaEntradas.get(i) >= pListaEntradas.get(i + 1)) {
		    throw new IllegalArgumentException(
				    "Lista de entrada contem instante fora de ordem (E[i] < E[i+i], para 1 ≤ i < N)");
		}
	    }
	}
    }

    private void verificarEntradaDiferenteDaSaida(List<Integer> pListaEntradas, List<Integer> pListaSaidas) {
	// E[i] ≠ S[j], para todo par i e j, 1 ≤ i ≤ N, 1 ≤ j ≤ N (Observação: j é uma segunda pessoa)

	int size = pListaEntradas.size() - 1;
	for (int i = 0; i < size; i++) {
	    if (i < size) {
		if (pListaEntradas.get(i) == pListaSaidas.get(i + 1)) {
		    throw new IllegalArgumentException(
				    "Listas contem instantes identicos para entrada e saida (E[i] ≠ S[j], para todo par i e j, 1 ≤ i ≤ N, 1 ≤ j ≤ N)");
		}
	    }
	}
    }

    private void verificarEntradaMenorQueSaida(int pEntrada, int pSaida) {
	// E[i] < S[i], para 1 ≤ i ≤ N

	if (pEntrada >= pSaida) {
	    throw new IllegalArgumentException("Listas contem entrada maior ou igual a saida (E[i] < S[i], para 1 ≤ i ≤ N)");
	}
    }

    private void verificarLimiteInstante(int pInstante) {
	// 1 ≤ E[i] ≤ 15.000, 1 ≤ i ≤ N
	// 1 ≤ S[i] ≤ 15.000, 1 ≤ i ≤ N

	if (pInstante < 1 || pInstante > 15000) {
	    throw new IllegalArgumentException("Lista contem instante fora do limite (1 ≤ A[i] ≤ 15.000, 1 ≤ i ≤ N)");
	}
    }

    private void verificarNotNull(List<Integer> pListaEntradas, List<Integer> pListaSaidas) {
	if (pListaEntradas == null || pListaSaidas == null) {
	    throw new IllegalArgumentException("Valor nulo informado como parametro");
	}
    }

    private void verificarTamanhoListas(List<Integer> pListaEntradas, List<Integer> pListaSaidas) {
	// 1 ≤ N ≤ 5.000

	int tamanhoEntrada = pListaEntradas.size();
	int tamanhoSaida = pListaSaidas.size();

	if (tamanhoEntrada != tamanhoSaida) {
	    throw new IllegalArgumentException("Tamanho da lista de entrada difere do tamanho da lista de saida");
	}

	if (tamanhoEntrada < 1 || tamanhoEntrada > 5000) {
	    throw new IllegalArgumentException("Tamanho da lista de entrada esta fora do limite (1 ≤ N ≤ 5.000)");
	}

	if (tamanhoSaida < 1 || tamanhoSaida > 5000) {
	    throw new IllegalArgumentException("Tamanho da lista de saida esta fora do limite (1 ≤ N ≤ 5.000)");
	}
    }
}
