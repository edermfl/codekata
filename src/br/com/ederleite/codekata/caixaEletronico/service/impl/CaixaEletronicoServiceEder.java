package br.com.ederleite.codekata.caixaEletronico.service.impl;

import br.com.ederleite.codekata.caixaEletronico.domain.ImpossivelSacarException;
import br.com.ederleite.codekata.caixaEletronico.domain.model.QuantidadeNotaTO;
import br.com.ederleite.codekata.caixaEletronico.service.ICaixaEletronicoService;

import java.math.BigDecimal;

/**
 * Created by eder on 12/02/2016.
 */
public class CaixaEletronicoServiceEder implements ICaixaEletronicoService {

    private QuantidadeNotaTO estoqueNotas;

    @Override
    public void contarNotas(final QuantidadeNotaTO pQuantidadeNotas) {
        estoqueNotas = pQuantidadeNotas;

    }

    @Override
    public QuantidadeNotaTO sacar(final BigDecimal pValor) throws ImpossivelSacarException{
	return null;
    }
}
