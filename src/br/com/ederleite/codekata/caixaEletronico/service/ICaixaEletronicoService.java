package br.com.ederleite.codekata.caixaEletronico.service;

import br.com.ederleite.codekata.caixaEletronico.domain.ImpossivelSacarException;
import br.com.ederleite.codekata.caixaEletronico.domain.model.QuantidadeNotaTO;

import java.math.BigDecimal;

/**
 * Created by eder on 12/02/2016.
 */
public interface ICaixaEletronicoService {
    public void contarNotas(QuantidadeNotaTO pQuantidadeNotas);

    public QuantidadeNotaTO sacar(BigDecimal pValor) throws ImpossivelSacarException;
}
