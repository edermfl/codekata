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
	final QuantidadeNotaTO retorno = new QuantidadeNotaTO(0, 0, 0, 0, 0);

	validarValorInformado(pValor);
	BigDecimal valor = pValor.setScale(0, RoundingMode.DOWN);

	//se valor for impar, já infiro que a quantidade de notas de 5 é 1, se não, dá erro.
	valor = calcularValorImpar(valor, retorno);
	//trata entrega de notas de R$ 50, tem uma pegadinha aqui!
	valor = calcularQuantidadeNotas50(valor, retorno);
	//trata entrega de notas de R$ 20
	valor = calcularQuantidadeNotas20(valor, retorno);
	//trata entrega de notas de R$ 10
	valor = calcularQuantidadeNotas10(valor, retorno);
	//trata entrega de notas de R$ 5, tem uma pegadinha aqui também
	valor = calcularQuantidadeNotas5(valor, retorno);
	//trata entrega de notas de R$ 2
	valor = calcularQuantidadeNotas2(valor, retorno);

	//Dá erro caso valo não esteja zerado.
	if (valor.compareTo(BigDecimal.ZERO) != 0) {
	    throw new ImpossivelSacarException("Impossivel sacar, estoque de notas insuficiente.");
	}
	return retorno;
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

    public boolean valorEhMultiploDe(final BigDecimal pValor, final BigDecimal pMultiploDe) {
	return pValor.remainder(pMultiploDe).compareTo(BigDecimal.ZERO) == 0;
    }

    private Integer calcularQuantidadeNotas(final BigDecimal pValor, final BigDecimal pValorNota) {
	if (pValor.compareTo(BigDecimal.ZERO) > 0 && pValor.compareTo(pValorNota) >= 0) {
	    return descobrirQuantidadeDeNotas(pValor, pValorNota);
	}
	return 0;
    }

    private BigDecimal calcularQuantidadeNotas10(final BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	Integer quantDez = calcularQuantidadeNotas(pValor, DEZ);
	pRetorno.setNotas10(quantDez);
	estoque.setNotas10(estoque.getNotas10() - quantDez);
	return calcularSobra(pValor, quantDez, DEZ);
    }

    private BigDecimal calcularQuantidadeNotas2(final BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	Integer quantDois = calcularQuantidadeNotas(pValor, DOIS);
	pRetorno.setNotas2(quantDois);
	estoque.setNotas2(estoque.getNotas2() - quantDois);
	return calcularSobra(pValor, quantDois, DOIS);
    }

    private BigDecimal calcularQuantidadeNotas20(final BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	Integer quantVinte = calcularQuantidadeNotas(pValor, VINTE);
	pRetorno.setNotas20(quantVinte);
	estoque.setNotas20(estoque.getNotas20() - quantVinte);
	return calcularSobra(pValor, quantVinte, VINTE);
    }

    private BigDecimal calcularQuantidadeNotas5(BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	Integer quantNotas5 = 0;
	if (estoque.getNotas5() == 0 || valorEhMultiploDe(pValor, CINCO)) {
	    quantNotas5 = calcularQuantidadeNotas(pValor, CINCO);
	    // a quantidade de nota 5 não deve ser impar, caso contrário não conseguirei dispensar o valor desejado.
	    final boolean ehImparQuantCinco = quantNotas5 % 2 == 1;
	    if (ehImparQuantCinco) {
		quantNotas5 -= 1;
	    }
	    pValor = calcularSobra(pValor, quantNotas5, CINCO);
	    estoque.setNotas5(estoque.getNotas5() + quantNotas5);
	}
	pRetorno.setNotas5(pRetorno.getNotas5() + quantNotas5);
	return pValor;
    }

    private BigDecimal calcularQuantidadeNotas50(BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	Integer quantNotas50 = calcularQuantidadeNotas(pValor, CINQUENTA);
	BigDecimal valor = calcularSobra(pValor, quantNotas50, CINQUENTA);
	if (!valorEhMultiploDe(valor, VINTE)) {
	    final boolean naoTemNota10 = obterQuantidadeNotaEstoque(DEZ) == 0;
	    final boolean naoTem2Notas5 = estoque.getNotas5() < 2;
	    final boolean tenhoNotas20Disponiveis =
			    obterQuantidadeNotaEstoque(VINTE) >= calcularQuantidadeNotas(valor.add(CINQUENTA), VINTE);
	    if (quantNotas50 % 2 == 1 && naoTemNota10 && naoTem2Notas5 && tenhoNotas20Disponiveis) {
		quantNotas50 -= 1;
	    }
	    valor = calcularSobra(pValor, quantNotas50, CINQUENTA);
	}
	estoque.setNotas50(estoque.getNotas50() - quantNotas50);
	pRetorno.setNotas50(quantNotas50);
	return valor;
    }

    private BigDecimal calcularSobra(final BigDecimal pValor, final Integer pQuant, final BigDecimal pValorNota) {
	return pValor.subtract(pValorNota.multiply(new BigDecimal(pQuant)));
    }

    private BigDecimal calcularValorImpar(BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	boolean ehImpar = !valorEhMultiploDe(pValor, new BigDecimal(2));
	Integer quantCinco = 0;
	if (ehImpar && estoque.getNotas5() > 0) {
	    quantCinco = 1;
	    pValor = pValor.subtract(CINCO);
	    estoque.setNotas5(estoque.getNotas5() - quantCinco);
	}
	pRetorno.setNotas5(quantCinco);
	return pValor;
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
