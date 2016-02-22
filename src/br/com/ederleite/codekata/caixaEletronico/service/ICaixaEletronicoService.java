package br.com.ederleite.codekata.caixaEletronico.service;

import br.com.ederleite.codekata.caixaEletronico.domain.ImpossivelSacarException;
import br.com.ederleite.codekata.caixaEletronico.domain.model.QuantidadeNotaTO;

import java.math.BigDecimal;

/**
 * Created by eder on 12/02/2016.
 */
public interface ICaixaEletronicoService {
    /** 
    * Método usado para informar o estoque de notas. Este método serve apenas para informar o estoque inicial de notas, 
    * funcionará como um setter para um atributo da classe. 
    * O nome do método não ficou legal.... Sorry!!
    * @param pQuantidadeNotas
    **/
    public void contarNotas(QuantidadeNotaTO pQuantidadeNotas);

    /**
     * Método retornará um objeto do tipo QuantidadeNotaTO com a quantidade de notas necessárias para compor o valor informado por parametro.
     * @param pValor
     * @throws ImpossivelSacarException
     **/
    public QuantidadeNotaTO sacar(BigDecimal pValor) throws ImpossivelSacarException;
}
