package br.com.ederleite.example.codekata.encontreSequencia.service;

import br.com.ederleite.example.codekata.encontreSequencia.domain.model.PosicaoTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by eml on 29/01/16.
 */
public class EncontrarSequenciaTest {

    @Test
    public void testEncontrar() throws Exception {

	final EncontrarSequencia service = new EncontrarSequencia();
	final PosicaoTO posicaoTO = service.encontrar("AB", "ABXYYXBAAB");

	final String posicoesDireta = StringUtils.join(posicaoTO.listaPosicoesDireta, "|");
	Assert.assertEquals("1|9", posicoesDireta);

	final String posicoesReversa = StringUtils.join(posicaoTO.listaPosicoesReversa, "|");
	Assert.assertEquals("7", posicoesReversa);

    }
}