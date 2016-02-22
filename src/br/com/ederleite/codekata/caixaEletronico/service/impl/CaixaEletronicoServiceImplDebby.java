package br.com.ederleite.codekata.caixaEletronico.service.impl;

import br.com.ederleite.codekata.caixaEletronico.domain.ImpossivelSacarException;
import br.com.ederleite.codekata.caixaEletronico.domain.model.QuantidadeNotaTO;
import br.com.ederleite.codekata.caixaEletronico.service.ICaixaEletronicoService;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by debby on 17/02/2016.
 * …
 */
public class CaixaEletronicoServiceImplDebby implements ICaixaEletronicoService {

    /**
     * Nota de 5
     */
    private static BigDecimal CINCO = BigDecimal.valueOf(5);

    /**
     * Nota de 50
     */
    private static BigDecimal CINQUENTA = BigDecimal.valueOf(50);

    /**
     * Nota de 10
     */
    private static BigDecimal DEZ = BigDecimal.TEN;

    /**
     * Nota de 2
     */
    private static BigDecimal DOIS = BigDecimal.valueOf(2);

    /**
     * Nota de 20
     */
    private static BigDecimal VINTE = BigDecimal.valueOf(20);

    /**
     * Notas em valor decrescente.
     */
    private static List<BigDecimal> notasPossiveis = Arrays.asList(CINQUENTA, VINTE, DEZ, CINCO, DOIS);

    /**
     * Valor máximo do saque
     */
    private static BigDecimal valorMaximo = BigDecimal.valueOf(10000);

    /**
     * Método usado para informar o estoque de notas. Este método serve apenas para informar o estoque inicial de notas,
     * funcionará como um setter para um atributo da classe.
     * O nome do método não ficou legal.... Sorry!!
     *
     * @param pQuantidadeNotas
     **/
    public void contarNotas(QuantidadeNotaTO pQuantidadeNotas) {
	Integer notas2 = pQuantidadeNotas.getNotas2();
	Integer notas5 = pQuantidadeNotas.getNotas5();
	Integer notas10 = pQuantidadeNotas.getNotas10();
	Integer notas20 = pQuantidadeNotas.getNotas20();
	Integer notas50 = pQuantidadeNotas.getNotas50();
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("Há ").append(notas2).append(" notas de R$ 2,00.\n");
	stringBuilder.append("Há ").append(notas5).append(" notas de R$ 5,00.\n");
	stringBuilder.append("Há ").append(notas10).append(" notas de R$ 10,00.\n");
	stringBuilder.append("Há ").append(notas20).append(" notas de R$ 20,00.\n");
	stringBuilder.append("Há ").append(notas50).append(" notas de R$ 50,00.\n\n");

	BigDecimal valorTotal = BigDecimal.ZERO;
	valorTotal = valorTotal.add(DOIS.multiply(BigDecimal.valueOf(notas2)));
	valorTotal = valorTotal.add(CINCO.multiply(BigDecimal.valueOf(notas5)));
	valorTotal = valorTotal.add(DEZ.multiply(BigDecimal.valueOf(notas10)));
	valorTotal = valorTotal.add(VINTE.multiply(BigDecimal.valueOf(notas20)));
	valorTotal = valorTotal.add(CINQUENTA.multiply(BigDecimal.valueOf(notas50)));

	Integer quantiaTotal = notas2 + notas5 + notas10 + notas20 + notas50;

	stringBuilder.append("Que totalizam R$").append(valorTotal).append(",00 reais, utilizando um total de ")
			.append(quantiaTotal).append(" notas!\n");

	System.out.println(stringBuilder.toString());

    }

    /**
     * Método retornará um objeto do tipo QuantidadeNotaTO com a quantidade de notas necessárias para compor o valor informado por parametro.
     *
     * @param pValor
     * @throws ImpossivelSacarException
     **/

    public QuantidadeNotaTO sacar(BigDecimal pValor) throws ImpossivelSacarException {

	// Se o valor for nulo, 0, 1, 3 reais ou negativo, não é possível sacar.
	if (pValor == null || pValor.compareTo(BigDecimal.ONE) == 0 || pValor.compareTo(BigDecimal.valueOf(3)) == 0 || pValor
			.compareTo(BigDecimal.ZERO) == 0)
	{
	    throw new ImpossivelSacarException(
			    "O valor excedeu o limite de saque de R$ 10.000,00 (dez mil reais), tente um valor menor.");
	}
	// O saque não pode ser de um valor com casas decimais, ou seja, de centavos.
	// Apenas números inteiros, mas deve considerar 4.00000 como inteiro.
	else if (pValor.toString().split("\\.").length > 1 && Integer.valueOf(pValor.toString().split("\\.")[1]) != 0) {
	    throw new ImpossivelSacarException("Não é possível sacar com centavos, tente outro valor.");
	}

	// Se for um número inteiro, mas com 0 à direita, devo tratar.
	if (pValor.toString().split("\\.").length > 1 && Integer.valueOf(pValor.toString().split("\\.")[1]) == 0) {
	    pValor = BigDecimal.valueOf(Long.parseLong(pValor.toString().split("\\.")[0]));
	}

	// Inicializo as quantias das notas.
	Map<Integer, Integer> notasESuasQuantias = new HashMap();
	notasESuasQuantias.put(2, 0);
	notasESuasQuantias.put(5, 0);
	notasESuasQuantias.put(10, 0);
	notasESuasQuantias.put(20, 0);
	notasESuasQuantias.put(50, 0);

	// Inicio o valor total que servirá de controle.
	BigDecimal valorTotal = BigDecimal.ZERO;

	// Se terminar em 1, 3, 6 ou 8, uso um algorítimo.
	boolean terminaEmUmTresSeisOuOito =
			String.valueOf(pValor.intValue()).endsWith("1") || String.valueOf(pValor.intValue()).endsWith("3")
					|| String.valueOf(pValor.intValue()).endsWith("6") || String.valueOf(pValor.intValue())
					.endsWith("8");
	if (terminaEmUmTresSeisOuOito) {
	    // Enquanto não for divísivel por 5 (scale > 0), adiciono notas de dois.
	    while (pValor.divide(CINCO).scale() > 0) {
		pValor = pValor.subtract(DOIS);
		valorTotal = valorTotal.add(DOIS);
		adicionarNota(notasESuasQuantias, DOIS);
	    }
	    // Se estiver terminando com 5, adiciono uma nota de 5.
	    if (String.valueOf(pValor.intValue()).endsWith("5")) {
		pValor = pValor.subtract(CINCO);
		valorTotal = valorTotal.add(CINCO);
		adicionarNota(notasESuasQuantias, CINCO);
	    }

	}
	// Se ainda não tiver zerado o valor digitado.
	if (pValor.compareTo(BigDecimal.ZERO) != 0) {

	    // Para cada nota possível
	    for (BigDecimal nota : notasPossiveis) {

		BigDecimal resultadoDivisao = pValor.divide(nota);
		// Se o resultado da divisão for maior que 0, desconsiderando os decimais (não pode ser 0,314...)
		if (resultadoDivisao.intValue() > 0) {
		    BigDecimal valorNotas = BigDecimal.valueOf(resultadoDivisao.intValue() * nota.intValue());
		    pValor = pValor.subtract(valorNotas);
		    for (int i = 0; i < resultadoDivisao.intValue(); i++) {
			valorTotal = valorTotal.add(valorNotas);
			adicionarNota(notasESuasQuantias, nota);
		    }
		}
		// Senão, vou pra próxima nota.
		else {
		    continue;
		}

		boolean valorDigitadoAlcancado = pValor.compareTo(BigDecimal.ZERO) == 0;
		if (valorDigitadoAlcancado) {
		    break;
		}
	    }
	}

	QuantidadeNotaTO quantidadeNotaTO = new QuantidadeNotaTO(notasESuasQuantias.get(2), notasESuasQuantias.get(5),
			notasESuasQuantias.get(10), notasESuasQuantias.get(20), notasESuasQuantias.get(50));

	return quantidadeNotaTO;
    }

    /**
     * Adiciona na contagens das notas + 1 conforme a nota informada.
     *
     * @param pMapaNotas
     * @param pNota
     */
    private void adicionarNota(Map<Integer, Integer> pMapaNotas, BigDecimal pNota) {
	if (pNota.compareTo(DOIS) == 0) {
	    pMapaNotas.put(2, pMapaNotas.get(2) + 1);
	} else if (pNota.compareTo(CINCO) == 0) {
	    pMapaNotas.put(5, pMapaNotas.get(5) + 1);
	} else if (pNota.compareTo(DEZ) == 0) {
	    pMapaNotas.put(10, pMapaNotas.get(10) + 1);
	} else if (pNota.compareTo(VINTE) == 0) {
	    pMapaNotas.put(20, pMapaNotas.get(20) + 1);
	} else if (pNota.compareTo(CINQUENTA) == 0) {
	    pMapaNotas.put(50, pMapaNotas.get(50) + 1);
	}
    }
}