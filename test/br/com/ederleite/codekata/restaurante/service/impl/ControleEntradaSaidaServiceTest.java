package br.com.ederleite.codekata.restaurante.service.impl;

import br.com.ederleite.codekata.restaurante.service.IControleEntradaSaidaService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eml on 02/03/16.
 */
public class ControleEntradaSaidaServiceTest {

    @Test
    public void testeCalcularMaximoPessoasSimultaneamenteNoRestaurante1() {
	IControleEntradaSaidaService service = new ControleEntradaSaidaServiceImpl();
	final List<Integer> listaEntradas = Arrays.asList(14, 67, 98);
	final List<Integer> listaSaidas = Arrays.asList(1890, 1900, 2123);
	final Integer quantidade = service.calcularMaximoPessoasSimultaneamenteNoRestaurante(listaEntradas, listaSaidas);
	Assert.assertTrue("Quantidade esperada não foi a retornada", 3 == quantidade);
    }

    @Test
    public void testeCalcularMaximoPessoasSimultaneamenteNoRestaurante2() {
	IControleEntradaSaidaService service = new ControleEntradaSaidaServiceImpl();
	final List<Integer> listaEntradas = Arrays.asList(200, 1800);
	final List<Integer> listaSaidas = Arrays.asList(1543, 2324);
	final Integer quantidade = service.calcularMaximoPessoasSimultaneamenteNoRestaurante(listaEntradas, listaSaidas);
	Assert.assertTrue("Quantidade esperada não foi a retornada", 1 == quantidade);
    }

}