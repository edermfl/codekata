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

    private BigDecimal valorTotalEmEstoque;

    @Override public void contarNotas(final QuantidadeNotaTO pQuantidadeNotas) {
	estoque = pQuantidadeNotas;
	valorTotalEmEstoque = new BigDecimal(
			pQuantidadeNotas.getNotas50() * 50 + pQuantidadeNotas.getNotas20() * 20
					+ pQuantidadeNotas.getNotas10() * 10 + pQuantidadeNotas.getNotas5() * 5
					+ pQuantidadeNotas.getNotas2() * 2);

    }

    @Override public QuantidadeNotaTO sacar(final BigDecimal pValor) throws ImpossivelSacarException {
	validarValorInformado(pValor);
	BigDecimal valor = pValor.setScale(0, RoundingMode.DOWN);

	//se valor for impar, já infiro que a quantidade de notas de 5 é 1, se não, dá erro.
	boolean ehImpar = valor.remainder(new BigDecimal(2)).compareTo(BigDecimal.ONE) == 0;
	Integer quantCinco = 0;
	if (ehImpar && obterQuantidadeNotaEstoque(CINCO) > 0) {
	    quantCinco = 1;
	    valor = valor.subtract(CINCO);
	}

	Integer quantCinquenta = calcularQuantidadeNotas(valor, CINQUENTA);
	valor = calcularSobra(valor, quantCinquenta, CINQUENTA);
	Integer quantVinte = calcularQuantidadeNotas(valor, VINTE);
	valor = calcularSobra(valor, quantVinte, VINTE);
	Integer quantDez = calcularQuantidadeNotas(valor, DEZ);
	valor = calcularSobra(valor, quantDez, DEZ);

	if (quantCinco == 0) {
	    quantCinco = calcularQuantidadeNotas(valor, CINCO);
	    // a quantidade de nota 5 não deve ser impar, caso contrário não conseguirei dispensar o valor desejado.
	    final boolean quantCincoEhImpar = quantCinco % 2 == 1;
	    if(quantCincoEhImpar){
		quantCinco -= 1;
	    }
	    valor = calcularSobra(valor, quantCinco, CINCO);
	}

	Integer quantDois = calcularQuantidadeNotas(valor, DOIS);
	valor = calcularSobra(valor, quantDois, DOIS);
	if (valor.compareTo(BigDecimal.ZERO) != 0) {
	    throw new ImpossivelSacarException("Impossivel sacar, estoque de notas insuficiente.");
	}
	return new QuantidadeNotaTO(quantDois, quantCinco, quantDez, quantVinte, quantCinquenta);
    }

    public void validarValorInformado(final BigDecimal pValor) throws ImpossivelSacarException {
	if (VALOR_MAXIMO_PERMITIDO.compareTo(pValor) < 0) {
	    throw new ImpossivelSacarException("Impossivel sacar, valor máximo (R$ 10.000,00) excedido.");
	}
	if (valorTotalEmEstoque.compareTo(pValor) < 0) {
	    throw new ImpossivelSacarException("Impossivel sacar, valor indisponivel no momento.");
	}
	final String valorString = pValor.setScale(2).toString();
	final String[] valorInteiroEDecimal = valorString.split("\\.");
	if (Integer.valueOf(valorInteiroEDecimal[1]) != 0) {
	    throw new ImpossivelSacarException("Impossivel sacar, apenas valores inteiros");
	}
	if (Arrays.asList("1", "3").contains(Integer.valueOf(valorInteiroEDecimal[0]))) {
	    throw new ImpossivelSacarException("Impossivel sacar, caixa não possui notas de R$ 1.");
	}
    }

    private Integer calcularQuantidadeNotas(final BigDecimal pValor, final BigDecimal pValorNota) {
	if (pValor.compareTo(BigDecimal.ZERO) > 0 && pValor.compareTo(pValorNota) >= 0) {
	    return descobrirQuantidadeDeNotas(pValor, pValorNota);
	}
	return 0;
    }

    private BigDecimal calcularSobra(final BigDecimal pValor, final Integer pQuant, final BigDecimal pValorNota) {
	return pValor.subtract(pValorNota.multiply(new BigDecimal(pQuant)));
    }

    private Integer descobrirQuantidadeDeNotas(final BigDecimal pValor, final BigDecimal pValorNota) {
	int quantidade = pValor.divide(pValorNota, 0, RoundingMode.DOWN).intValue();
	final Integer quantNotasEstoque = obterQuantidadeNotaEstoque(pValorNota);
	return quantidade <= quantNotasEstoque ? quantidade : quantNotasEstoque;
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


}
