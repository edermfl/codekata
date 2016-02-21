package br.com.ederleite.codekata.caixaEletronico.service.impl;

import br.com.ederleite.codekata.caixaEletronico.domain.ImpossivelSacarException;
import br.com.ederleite.codekata.caixaEletronico.domain.model.QuantidadeNotaTO;
import br.com.ederleite.codekata.caixaEletronico.service.ICaixaEletronicoService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fcs on 20/02/16.
 */
public class CaixaEletronicoServiceImpl implements ICaixaEletronicoService {

    private Map<Integer,Integer> qtdNotas = new HashMap<Integer,Integer>();

    @Override public void contarNotas(final QuantidadeNotaTO pQuantidadeNotas) {
	qtdNotas.put(50, pQuantidadeNotas.getNotas50());
	qtdNotas.put(20, pQuantidadeNotas.getNotas20());
	qtdNotas.put(10, pQuantidadeNotas.getNotas10());
	qtdNotas.put(5, pQuantidadeNotas.getNotas5());
	qtdNotas.put(2, pQuantidadeNotas.getNotas2());
    }

    @Override public QuantidadeNotaTO sacar(BigDecimal pValor) throws ImpossivelSacarException {
	QuantidadeNotaTO retorno = new QuantidadeNotaTO();

	verificaRestricoes(pValor);
	pValor = usaNotasDe50(pValor, retorno);
	pValor = usaNotasDe20(pValor, retorno);
	pValor = usaNotasDe10(pValor, retorno);
	pValor = usaNotasDe5(pValor, retorno);
	pValor = usaNotasDe2(pValor, retorno);

	if (pValor.compareTo(BigDecimal.ZERO) != 0) {
	    throw new ImpossivelSacarException("Impossivel sacar!! Valor restante: " + pValor);
	}
	return retorno;
    }

    private BigDecimal decrementaValorEm(final int pValorNota, final BigDecimal pValor) {
	return pValor.subtract(new BigDecimal(pValorNota));
    }

    private void devolveNotaDe(final int pNota) {
	qtdNotas.put(pNota, qtdNotas.get(pNota) + 1);
    }

    private BigDecimal incrementaValorEm(final int pValorNota, final BigDecimal pValor) {
	return pValor.add(new BigDecimal(pValorNota));
    }

    private BigDecimal usaNotasDe10(BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	while (pValor.compareTo(new BigDecimal(10)) != -1) {
	    if (usouNotaDe(10)) {
		pValor = decrementaValorEm(10, pValor);
		if (pValor.compareTo(new BigDecimal(10)) == -1 && !valorEhMultiploDe(5, pValor) && !valorEhMultiploDe(2,
				pValor))
		{
		    devolveNotaDe(10);
		    pValor = incrementaValorEm(10, pValor);
		    break;
		}
//		pRetorno.adicionaNotaDe10();
	    } else {
		break;
	    }
	}
	return pValor;
    }

    private BigDecimal usaNotasDe2(BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	while (pValor.compareTo(new BigDecimal(2)) != -1) {
	    if (usouNotaDe(2)) {
		pValor = decrementaValorEm(2, pValor);
//		pRetorno.adicionaNotaDe2();
	    } else {
		break;
	    }
	}
	return pValor;
    }

    private BigDecimal usaNotasDe20(BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	while (pValor.compareTo(new BigDecimal(20)) != -1) {
	    if (usouNotaDe(20)) {
		pValor = decrementaValorEm(20, pValor);
		if (pValor.compareTo(new BigDecimal(10)) == -1 && !valorEhMultiploDe(10, pValor) && !valorEhMultiploDe(5, pValor)
				&& !valorEhMultiploDe(2, pValor))
		{
		    devolveNotaDe(20);
		    pValor = incrementaValorEm(20, pValor);
		    break;
		}
//		pRetorno.adicionaNotaDe20();
	    } else {
		break;
	    }
	}
	return pValor;
    }

    private BigDecimal usaNotasDe5(BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	while (pValor.compareTo(new BigDecimal(5)) != -1) {
	    if (usouNotaDe(5)) {
		pValor = decrementaValorEm(5, pValor);
		if (pValor.compareTo(new BigDecimal(5)) == -1 && !valorEhMultiploDe(2, pValor)) {
		    devolveNotaDe(5);
		    pValor = incrementaValorEm(5, pValor);
		    break;
		}
//		pRetorno.adicionaNotaDe5();
	    } else {
		break;
	    }
	}
	return pValor;
    }

    private BigDecimal usaNotasDe50(BigDecimal pValor, final QuantidadeNotaTO pRetorno) {
	while (pValor.compareTo(new BigDecimal(50)) != -1) {
	    if (usouNotaDe(50)) {
		pValor = decrementaValorEm(50, pValor);
		if (pValor.compareTo(new BigDecimal(1)) == 0 || pValor.compareTo(new BigDecimal(3)) == 0) {
		    devolveNotaDe(50);
		    pValor = incrementaValorEm(50, pValor);
		    break;
		}
//		pRetorno.adicionaNotaDe50();
	    } else {
		break;
	    }
	}
	return pValor;
    }

    private boolean usouNotaDe(final int pNota) {
	if (qtdNotas.get(pNota) > 0) {
	    qtdNotas.put(pNota, qtdNotas.get(pNota) - 1);
	    return true;
	}
	return false;
    }

    private boolean valorEhMultiploDe(final int pNota, final BigDecimal pValor) {
	final BigDecimal resto = pValor.remainder(new BigDecimal(pNota));
	return (resto.compareTo(BigDecimal.ZERO) == 0 ? true : false);
    }

    private void verificaRestricoes(final BigDecimal pValor) throws ImpossivelSacarException {
	if (pValor.compareTo(BigDecimal.ZERO) == -1 || pValor.compareTo(BigDecimal.ZERO) == 0
			|| pValor.compareTo(new BigDecimal(10000)) == 1)
	{
	    throw new ImpossivelSacarException("O valor: " + pValor + " é menor que 0 ou maior que 10000");
	}
    }
}