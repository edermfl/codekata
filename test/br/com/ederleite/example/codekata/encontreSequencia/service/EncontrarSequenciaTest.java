package br.com.ederleite.example.codekata.encontreSequencia.service;

import br.com.ederleite.example.codekata.encontreSequencia.domain.model.PosicaoTO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by eml on 29/01/16.
 */
public class EncontrarSequenciaTest {

    /**
     * ISO8859 = "ISO-8859-1"
     */
    public static final String ISO88591 = "ISO-8859-1";

    @Test
    public void test() {
	List<String> caracteresValidos = Arrays.asList("A", "B", "X", "Y");
	for (int cenario = 2; cenario < 3; cenario++) {

	    Random gerador = new Random();
	    final int tamanhoSequencia = 5000;

	    StringBuilder sequencia = new StringBuilder();
	    for (int i = 0; i < tamanhoSequencia; i++) {
		final int numero = gerador.nextInt(4);
		sequencia.append(caracteresValidos.get(numero));
	    }

	    final int tamanhoProcura = 10 + 1;
	    StringBuilder procura = new StringBuilder();
	    for (int i = 0; i < tamanhoProcura; i++) {
		final int numero = gerador.nextInt(4);
		procura.append(caracteresValidos.get(numero));
	    }

	    System.out.println(String.format("|%s|%s|%s|", cenario, procura, sequencia));
	}
    }

    @Test
    public void testEncontrar() throws Exception {

	final EncontrarSequencia service = new EncontrarSequencia();
	final PosicaoTO posicaoTO = service.encontrar("AB", "ABXYYXBAAB");

	final String posicoesDireta = StringUtils.join(posicaoTO.listaPosicoesDireta, "|");
	Assert.assertEquals("1|9", posicoesDireta);

	final String posicoesReversa = StringUtils.join(posicaoTO.listaPosicoesReversa, "|");
	Assert.assertEquals("7", posicoesReversa);

    }

    @Test
    public void testEncontrarTestTable() throws IOException {
	final EncontrarSequencia service = new EncontrarSequencia();
	// leio o arquivo texto, e transformo-o em uma lista de strings
	final File testTable = new File(
			"test/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/testTable.txt");
	System.out.println(testTable.getAbsolutePath());
	final FileInputStream inputStream = new FileInputStream(testTable);
	final List<String> linhasTestTable = IOUtils.readLines(inputStream, ISO88591);
	linhasTestTable.remove(0);
	// os erro serão adicionados nessa lista
	final List<String> erros = new ArrayList<String>();
	for (String linhaTxt : linhasTestTable) {
	    if (linhaTxt.isEmpty() || linhaTxt.startsWith("!--")) {
		continue;
	    }
	    final String[] linha = StringUtils.splitPreserveAllTokens(linhaTxt.substring(1, linhaTxt.length() - 1), '|');

	    final String cenario = linha[0];
	    final String procurado = linha[1];
	    final String sequencia = linha[2];
	    final String direta = linha[3];
	    final String reversa = linha[4];
	    final String erro = linha[5];

	    try {
		final PosicaoTO posicaoTO = service.encontrar(procurado, sequencia);

		final String posicoesDireta = StringUtils.join(posicaoTO.listaPosicoesDireta, ";");
		if (!direta.equals(posicoesDireta)) {
		    erros.add("Cenário " + cenario + ": Falha direta. Esperado (" + direta + "), mas retornado (" + posicoesDireta
				    + ")");
		}

		final String posicoesReversa = StringUtils.join(posicaoTO.listaPosicoesReversa, ";");
		if (!reversa.equals(posicoesReversa)) {
		    erros.add("Cenário " + cenario + ": Falha reversa. Esperado (" + reversa + "), mas retornado ("
				    + posicoesReversa + ")");
		}
	    } catch (Throwable e) {
		if (StringUtils.isEmpty(erro)) {
		    erros.add("Cenário " + cenario + ": Era esperado lançar uma exceção!");
		}
	    }
	}

	// se lista de erros contiver algum item, então falho o teste e exibo os erros.
	if (!erros.isEmpty()) {
	    Assert.fail("Os Seguintes cenários de teste falharam: \n" + StringUtils.join(erros, "\n"));
	}
    }

}