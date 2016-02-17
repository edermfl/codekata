package br.com.ederleite.codekata.caixaEletronico.service.impl;

import br.com.ederleite.codekata.caixaEletronico.domain.ImpossivelSacarException;
import br.com.ederleite.codekata.caixaEletronico.domain.model.QuantidadeNotaTO;
import br.com.ederleite.codekata.caixaEletronico.service.ICaixaEletronicoService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Created by eml on 15/02/16.
 */
public class CaixaEletronicoServiceImplEder implements ICaixaEletronicoService {

    public static final BigDecimal VALOR_MAXIMO_PERMITIDO = new BigDecimal(10000);

    private static BigDecimal CINCO = new BigDecimal(5);

    private static BigDecimal CINQUENTA = new BigDecimal(50);

    private static BigDecimal DEZ = new BigDecimal(10);

    private static BigDecimal DOIS = new BigDecimal(2);

    private static BigDecimal VINTE = new BigDecimal(20);

    private QuantidadeNotaTO estoque;

    @Override public void contarNotas(final QuantidadeNotaTO pQuantidadeNotas) {
	estoque = pQuantidadeNotas;

    }

    @Override public QuantidadeNotaTO sacar(final BigDecimal pValor) throws ImpossivelSacarException {
	validarValorInformado(pValor);

	BigDecimal valor = pValor.setScale(0, RoundingMode.DOWN);
	Integer quantCinquenta = calcularQuantidadeNotas(valor, CINQUENTA);
	valor = calcularSobra(valor, quantCinquenta, CINQUENTA);
	Integer quantVinte = calcularQuantidadeNotas(valor, VINTE);
	valor = calcularSobra(valor, quantVinte, VINTE);
	Integer quantDez = calcularQuantidadeNotas(valor, DEZ);
	valor = calcularSobra(valor, quantDez, DEZ);
	Integer quantCinco = calcularQuantidadeNotas(valor, CINCO);
	valor = calcularSobra(valor, quantCinco, CINCO);
	Integer quantDois = calcularQuantidadeNotas(valor, DOIS);
	valor = calcularSobra(valor, quantDois, DOIS);
	if (valor.compareTo(BigDecimal.ZERO) != 0) {
	    throw new ImpossivelSacarException("Impossível sacar, estoque de notas insuficiente.");
	}
	return new QuantidadeNotaTO(quantDois, quantCinco, quantDez, quantVinte, quantCinquenta);
    }

    public void validarValorInformado(final BigDecimal pValor) throws ImpossivelSacarException {
	if (VALOR_MAXIMO_PERMITIDO.compareTo(pValor) < 0) {
	    throw new ImpossivelSacarException("Impossível sacar, valor máximo (R$ 10.000,00) excedido.");
	}
	final String valorString = pValor.setScale(2).toString();
	final String[] valorInteiroEDecimal = valorString.split("\\.");
	if (Integer.valueOf(valorInteiroEDecimal[1]) != 0) {
	    throw new ImpossivelSacarException("Impossível sacar, apenas valores inteiros");
	}
	if (Arrays.asList("1", "3").contains(Integer.valueOf(valorInteiroEDecimal[0]))) {
	    throw new ImpossivelSacarException("Impossível sacar, caixa não possui notas de R$ 1.");
	}
    }

    private Integer calcularQuantidadeNotas(final BigDecimal pValor, final BigDecimal pValorNota) {
	final boolean valorMaiorQZero = pValor.compareTo(BigDecimal.ZERO) > 0;
	if (valorMaiorQZero && valorMaiorIgualQue(pValor, pValorNota)) {
	    return descobrirQuantidadeDeNotas(pValor, pValorNota);
	}
	return 0;
    }

    private BigDecimal calcularSobra(final BigDecimal pValor, final Integer pQuant, final BigDecimal pValorNota) {
	return pValor.subtract(multiplicarValorNotaPorQuantidade(pValorNota, pQuant));
    }

    private Integer descobrirQuantidadeDeNotas(final BigDecimal pValor, final BigDecimal pValorNota) {
	final int quantidade = pValor.divide(pValorNota, 0, RoundingMode.DOWN).intValue();
	final Integer quantNotasEstoque = obterQuantidadeNotaEstoque(pValorNota);
	return quantidade <= quantNotasEstoque ? quantidade : quantNotasEstoque;
    }

    private BigDecimal multiplicarValorNotaPorQuantidade(final BigDecimal pValorNota, final Integer pQuantCinquenta) {
	return pValorNota.multiply(new BigDecimal(pQuantCinquenta));
    }

    private Integer obterQuantidadeNotaEstoque(final BigDecimal pValorNota) {
	if (CINQUENTA.equals(pValorNota)) {
	    return estoque.getNotas50();
	}
	if (VINTE.equals(pValorNota)) {
	    return estoque.getNotas20();
	}
	if (DEZ.equals(pValorNota)) {
	    return estoque.getNotas10();
	}
	if (CINCO.equals(pValorNota)) {
	    return estoque.getNotas5();
	}
	return estoque.getNotas2();
    }

    private boolean valorMaiorIgualQue(final BigDecimal pValor, final BigDecimal pValorNota) {
	return pValor.compareTo(pValorNota) >= 0;
    }

}
