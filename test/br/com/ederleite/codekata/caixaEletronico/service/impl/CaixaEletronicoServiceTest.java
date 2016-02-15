package br.com.ederleite.codekata.caixaEletronico.service.impl;

import br.com.ederleite.codekata.caixaEletronico.domain.ImpossivelSacarException;
import br.com.ederleite.codekata.caixaEletronico.domain.model.QuantidadeNotaTO;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by eml on 15/02/16.
 */
public class CaixaEletronicoServiceTest {

    @Test
    public void testSacar() throws Exception {

	final CaixaEletronicoServiceImplEder caixaEletronico = new CaixaEletronicoServiceImplEder();
	caixaEletronico.contarNotas(new QuantidadeNotaTO(10, 10, 10, 10, 10));

	QuantidadeNotaTO saque = caixaEletronico.sacar(new BigDecimal(50));
	Assert.assertEquals("1;0;0;0;0", saque.toString());

	saque = caixaEletronico.sacar(new BigDecimal(10));
	Assert.assertEquals("0;0;1;0;0", saque.toString());

	saque = caixaEletronico.sacar(new BigDecimal(15));
	Assert.assertEquals("0;0;1;1;0", saque.toString());

	saque = caixaEletronico.sacar(new BigDecimal(35));
	Assert.assertEquals("0;1;1;1;0", saque.toString());

	saque = caixaEletronico.sacar(new BigDecimal(92));
	Assert.assertEquals("1;2;0;0;1", saque.toString());

	saque = caixaEletronico.sacar(new BigDecimal(57));
	Assert.assertEquals("1;0;0;1;1", saque.toString());

    }


    @Test(expected = ImpossivelSacarException.class)
    public void testSacarValorInvalido_Centavos() throws Exception {

	final CaixaEletronicoServiceImplEder caixaEletronico = new CaixaEletronicoServiceImplEder();
	caixaEletronico.contarNotas(new QuantidadeNotaTO(10,10,10,10,10));

	caixaEletronico.sacar(new BigDecimal("50.15"));
    }

    @Test(expected = ImpossivelSacarException.class)
    public void testSacarValorInvalido_ValorNaoSuportado() throws Exception {

	final CaixaEletronicoServiceImplEder caixaEletronico = new CaixaEletronicoServiceImplEder();
	caixaEletronico.contarNotas(new QuantidadeNotaTO(10,10,10,10,10));

	caixaEletronico.sacar(new BigDecimal("51"));
    }

    @Test(expected = ImpossivelSacarException.class)
    public void testSacarValorInvalido_ValorNaoSuportado2() throws Exception {

	final CaixaEletronicoServiceImplEder caixaEletronico = new CaixaEletronicoServiceImplEder();
	caixaEletronico.contarNotas(new QuantidadeNotaTO(10,10,10,10,10));

	caixaEletronico.sacar(new BigDecimal("53"));
    }













    @Test(expected = ImpossivelSacarException.class)
    public void testSacarValorInvalido_ValorNaoSuportado3() throws Exception {
	CaixaEletronicoServiceImplEder cK2 = new CaixaEletronicoServiceImplEder();
	cK2.contarNotas(new QuantidadeNotaTO(10, 10, 10, 10, 0));
	cK2.sacar(BigDecimal.ONE);
    }

}