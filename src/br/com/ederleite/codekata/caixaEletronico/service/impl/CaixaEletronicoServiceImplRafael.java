package br.com.ederleite.codekata.caixaEletronico.service.impl;

import br.com.ederleite.codekata.caixaEletronico.domain.ImpossivelSacarException;
import br.com.ederleite.codekata.caixaEletronico.domain.model.QuantidadeNotaTO;
import br.com.ederleite.codekata.caixaEletronico.service.ICaixaEletronicoService;

import java.math.BigDecimal;

/**
 * Created by rafael.alves on 15/02/2016.
 * Um caixa eletr?nico opera com alguns tipos de notas dispon?veis (R$ 50,00, R$ 20,00, R$ 10,00, R$ 5,00 e R$2,00),
 * mantendo um estoque de c?dulas para cada valor. Os clientes do banco, podem efetuar saques de um certo valor.
 * Escreva um programa que, dado o valor solicitado (v) pelo cliente, determine o n?mero de cada uma das notas necess?rio
 * para totalizar esse valor, de modo a minimizar a quantidade de c?dulas entregues.
 * Exemplo 1: Se o cliente deseja retirar R$ 50,00, basta entregar uma ?nica nota de cinquenta reais.
 * Exemplo 2: Se o cliente deseja retirar R$ 92,00, ? necess?rio entregar uma nota de R$50,00, duas de R$ 20,00 e uma de R$2,00.
 * Restri??es
 * 0 < v = 10000.
 * Estoque de notas: dever? se informado pelo QuantidadeNotaTO.
 */
public class CaixaEletronicoServiceImplRafael implements ICaixaEletronicoService {
    int notasCinco = 0;

    int notasCinquenta = 0;

    int notasDez = 0;

    int notasDois = 0;

    int notasVinte = 0;

    QuantidadeNotaTO numerNotaTO;

    public void contarNotas(QuantidadeNotaTO pQuantidadeNotas) {
	this.numerNotaTO = pQuantidadeNotas;
    }

    public boolean possoProcessarNotasCinco(BigDecimal pValor) {
	boolean ehPermitidoNotasCinco;
	if (pValor.intValue() % 5 == 0) {
	    ehPermitidoNotasCinco = true;
	} else {
	    ehPermitidoNotasCinco = false;
	}
	return ehPermitidoNotasCinco;
    }

    public QuantidadeNotaTO sacar(BigDecimal pValor) throws ImpossivelSacarException {
	int ultimoNumero = verificarUltimoDigitoEhImpar(pValor);
	if (ultimoNumero % 2 != 0) {
	    while (ultimoNumero != 5) {
		if (ultimoNumero % 2 != 0 || ultimoNumero % 5 != 0) {
		    if (notasDois <= numerNotaTO.getNotas2()) {
			pValor = BigDecimal.valueOf(pValor.intValue() - 2);
			notasDois = notasDois + 1;
		    }
		}
		ultimoNumero = verificarUltimoDigitoEhImpar(pValor);
	    }
	}
	pValor = sacarNotas50(pValor);
	pValor = sacarNotas20(pValor);
	pValor = sacarNotas10(pValor);
	pValor = sacarNotas5(pValor);
	pValor = sacarNotas2(pValor);

	if (pValor.intValue() != 0) {
	    throw new ImpossivelSacarException("Não foi possível efetuar o saque!!!");

	}
	QuantidadeNotaTO resultado = new QuantidadeNotaTO(notasDois, notasCinco, notasDez, notasVinte, notasCinquenta);
	return resultado;
    }

    public BigDecimal sacarNotas10(BigDecimal pValor) {
	notasDez = pValor.intValue() / 10;
	if (notasDez <= numerNotaTO.getNotas10()) {
	    pValor = BigDecimal.valueOf((pValor.intValue() - (notasDez * 10)));
	} else {
	    pValor = BigDecimal.valueOf((pValor.intValue() - (numerNotaTO.getNotas10() * 10)));
	    notasDez = numerNotaTO.getNotas10();
	}
	return pValor;
    }

    public BigDecimal sacarNotas2(BigDecimal pValor) {
	notasDois = notasDois + pValor.intValue() / 2;
	if (pValor.intValue() != 0) {

	    if (notasDois <= numerNotaTO.getNotas2()) {
		pValor = BigDecimal.valueOf((pValor.intValue() - (notasDois * 2)));
	    } else {
		pValor = BigDecimal.valueOf((pValor.intValue() - (numerNotaTO.getNotas2() * 2)));
		notasDois = numerNotaTO.getNotas2();
	    }
	}
	return pValor;
    }

    public BigDecimal sacarNotas20(BigDecimal pValor) {
	notasVinte = pValor.intValue() / 20;
	if (notasVinte <= numerNotaTO.getNotas20()) {
	    pValor = BigDecimal.valueOf((pValor.intValue() - (notasVinte * 20)));
	} else {
	    pValor = BigDecimal.valueOf((pValor.intValue() - (numerNotaTO.getNotas20() * 20)));
	    notasVinte = numerNotaTO.getNotas20();
	}
	return pValor;
    }

    public BigDecimal sacarNotas5(BigDecimal pValor) {
	if (possoProcessarNotasCinco(pValor)) {
	    notasCinco = pValor.intValue() / 5;
	    if (notasCinco <= numerNotaTO.getNotas5()) {
		pValor = BigDecimal.valueOf((pValor.intValue() - (notasCinco * 5)));
	    } else {
		pValor = BigDecimal.valueOf((pValor.intValue() - (numerNotaTO.getNotas5() * 5)));
		notasCinco = numerNotaTO.getNotas5();
	    }
	}
	return pValor;
    }

    public BigDecimal sacarNotas50(BigDecimal pValor) {
	notasCinquenta = pValor.intValue() / 50;
	if (notasCinquenta <= numerNotaTO.getNotas50()) {
	    pValor = BigDecimal.valueOf((pValor.intValue() - (notasCinquenta * 50)));
	} else {
	    pValor = BigDecimal.valueOf(pValor.intValue() - (numerNotaTO.getNotas50() * 50));
	    notasCinquenta = numerNotaTO.getNotas50();
	}

	return pValor;
    }

    public int verificarUltimoDigitoEhImpar(BigDecimal pValor) {
	String numero = Integer.toString(pValor.intValue());
	int ultimoNumero = Integer.parseInt(numero.substring(numero.length() - 1));
	return ultimoNumero;
    }
}