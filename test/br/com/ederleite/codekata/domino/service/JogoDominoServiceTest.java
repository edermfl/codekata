package br.com.ederleite.codekata.domino.service;

import br.com.ederleite.codekata.domino.domain.model.PecaDomino;
import br.com.ederleite.codekata.domino.domain.model.Tabuleiro;
import br.com.ederleite.codekata.domino.service.impl.JogoDominoServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by eml on 24/03/16.
 */
public class JogoDominoServiceTest {

    @Test
    public void testJogarPecasEncaixadas() throws Exception {
	IJogoDominoService service = new JogoDominoServiceImpl();

	/**
	 * 0 1
	 * 2 1
	 * 2 0
	 */
	final List<PecaDomino> listaPecas = Arrays.asList(new PecaDomino(0, 1), new PecaDomino(2, 1), new PecaDomino(2, 0));
	final Tabuleiro resultado = service.jogar(listaPecas);
	System.out.println(resultado);

	Assert.assertEquals(Arrays.asList(new PecaDomino(0, 1), new PecaDomino(1, 2), new PecaDomino(2, 0).toString()),
			resultado.getPecasEncaixadas().toString());

	Assert.assertTrue(resultado.getPecasSobraram().isEmpty());

    }

    @Test
    public void testJogarPecasEncaixadasComSobra() throws Exception {
	IJogoDominoService service = new JogoDominoServiceImpl();
	/**
	 * 1 1
	 * 0 1
	 * 2 2
	 */
	final List<PecaDomino> listaPecas = Arrays.asList(new PecaDomino(1, 1), new PecaDomino(0, 1), new PecaDomino(2, 2));
	final Tabuleiro resultado = service.jogar(listaPecas);
	System.out.println(resultado);

	Assert.assertEquals(Arrays.asList(new PecaDomino(1, 1), new PecaDomino(1, 0)).toString(),
			resultado.getPecasEncaixadas().toString());

	Assert.assertEquals(Arrays.asList(new PecaDomino(2, 0)).toString(), resultado.getPecasSobraram().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJogarPecasEncaixadasRestricaoInfrigida() throws Exception {
	IJogoDominoService service = new JogoDominoServiceImpl();
	/**
	 * lista peças vazias
	 */
	final List<PecaDomino> listaPecas = new ArrayList<PecaDomino>();
	final Tabuleiro resultado = service.jogar(listaPecas);
    }

}