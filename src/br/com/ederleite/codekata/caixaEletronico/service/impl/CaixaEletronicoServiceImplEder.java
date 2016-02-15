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
    private QuantidadeNotaTO estoque;

    private static BigDecimal CINQUENTA = new BigDecimal(50);
    private static BigDecimal VINTE = new BigDecimal(20);
    private static BigDecimal DEZ = new BigDecimal(10);
    private static BigDecimal CINCO = new BigDecimal(5);
    private static BigDecimal DOIS = new BigDecimal(2);

    @Override public void contarNotas(final QuantidadeNotaTO pQuantidadeNotas) {
	estoque = pQuantidadeNotas;

    }

    @Override public QuantidadeNotaTO sacar(final BigDecimal pValor) throws ImpossivelSacarException {
	final String valorString = pValor.setScale(2).toString();
	final String[] valorInteiroEDecimal = valorString.split("\\.");
	if(Integer.valueOf( valorInteiroEDecimal[1]) != 0){
	    throw new ImpossivelSacarException("Impossível sacar, apenas valores inteiros");
	}
	if(Arrays.asList("1","3").contains(valorInteiroEDecimal[0].charAt(valorInteiroEDecimal[0].length()-1)) ){
	    throw new ImpossivelSacarException("Impossível sacar, caixa não possui notas de R$ 1.");
	}

	BigDecimal valor = pValor;
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
	if (valor.compareTo(BigDecimal.ZERO) != 0){
	    throw new ImpossivelSacarException("Impossível sacar, estoque de notas insuficiente.");
	}
	return new QuantidadeNotaTO(quantDois, quantCinco, quantDez, quantVinte, quantCinquenta);
    }

    private BigDecimal calcularSobra(final BigDecimal pValor, final Integer pQuant, final BigDecimal pValorNota) {
	return pValor.subtract(multiplicarValorNotaPorQuantidade(pValorNota, pQuant));
    }

    private Integer calcularQuantidadeNotas(final BigDecimal pValor, final BigDecimal pValorNota) {
	final boolean valorMaiorQZero = pValor.compareTo(BigDecimal.ZERO) > 0;
	if(valorMaiorQZero && valorMaiorIgualQue(pValor, pValorNota)){
	    return descobrirQuantidadeDeNotas(pValor, pValorNota);
	}
	return 0;
    }

    private BigDecimal multiplicarValorNotaPorQuantidade(final BigDecimal pValorNota, final Integer pQuantCinquenta) {
	return pValorNota.multiply(new BigDecimal(pQuantCinquenta));
    }

    private Integer descobrirQuantidadeDeNotas(final BigDecimal pValor, final BigDecimal pValorNota) {
	return pValor.divide(pValorNota, 0, RoundingMode.DOWN).intValue();
    }

    private boolean valorMaiorIgualQue(final BigDecimal pValor, final BigDecimal pValorNota) {
	return pValor.compareTo(pValorNota) >= 0;
    }

}
