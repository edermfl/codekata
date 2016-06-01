package br.com.ederleite.codekata.numeroErdos.service

import br.com.ederleite.codekata.numeroErdos.service.impl.NumeroErdosServiceImplLucas
import br.com.ederleite.codekata.util.CodekataUtil
import org.apache.commons.lang3.StringUtils
import org.junit.Assert
import org.junit.Test
/**
 * Created by eder on 08/05/2016.
 */
public class NumeroErdosServiceTest {
    private final INumeroErdosService service = new NumeroErdosServiceImplLucas();

    @Test
    public void testNumeroErdosTestTable() throws IOException {
        //inicio do teste
        def inicioTeste = System.currentTimeMillis()

        // leio o arquivo texto, e transformo-o em uma lista de strings
        final List<String> linhasTestTable = CodekataUtil.obterCenariosTestTable(this.getClass());
        // os erro ser�o adicionados nessa lista
        final List<String> erros = new ArrayList<String>();
        for (String linhaTxt : linhasTestTable) {
            final String[] linha = StringUtils.splitPreserveAllTokens(linhaTxt.substring(1, linhaTxt.length() - 1), '|');

            //|cenario|nomeAutor|arquivoListaArtigos|retorno|ok|
            final String cenario = linha[0];
            final String nomeAutor = linha[1]
            final nomeArquivo = "${linha[2]}.artigo"
            final List<String> listaAutoresPorArtigos = CodekataUtil.obterLinhasDoArquivo(this.class, nomeArquivo)
            final Integer esperado = (linha[3] ? linha[3] : null) as Integer
            def resultadoEsperado = linha[4];

            final boolean erroEsperado = 'erro' == resultadoEsperado || 'ERRO' == resultadoEsperado;
            Integer numeroErdos
            def inicioCenario
            def fimCenario
            def mensagemFalha = "\n - NomeAutor: $nomeAutor \n - Artigos:\n       -> ${listaAutoresPorArtigos.join("\n       -> ")}  \n - Saida: %s\n"
            try {
                inicioCenario = System.currentTimeMillis()
                numeroErdos = service.descobrirNumeroErdosDoAutor(nomeAutor, listaAutoresPorArtigos);
                fimCenario = System.currentTimeMillis()

                if (erroEsperado) {
                    erros.add("Cenario " + cenario + " falhou: pois um erro era esperado. Tempo execução: ${fimCenario - inicioCenario} milisegundos. " + String.format(mensagemFalha, numeroErdos));
                    continue;
                }

                if (esperado != numeroErdos) {
                    erros.add("Cenario $cenario falhou: Esperado $esperado, mas retornado $numeroErdos. Tempo execução: ${fimCenario - inicioCenario} milisegundos." + String.format(mensagemFalha, numeroErdos));
                    continue;
                }

            } catch (Throwable e) {
                fimCenario = System.currentTimeMillis()
                if (!erroEsperado) {
                    erros.add("Cenario $cenario falhou: pois NAO era esperado erro neste cenario. Tempo execução: ${fimCenario - inicioCenario} milisegundos. ${e.class.name} ${e.getMessage()}" + String.format(mensagemFalha, numeroErdos));
                    continue;
                }
            }
            println("Cenario " + cenario + ": ok! Tempo execução: ${fimCenario - inicioCenario} milisegundos");
        }

        def fimTeste = System.currentTimeMillis()
        println("==== Tempo execução total do teste: ${fimTeste - inicioTeste} milisegundos ====");

        // se lista de erros contiver algum item, ent�o falho o teste e exibo os erros.
        if (!erros.isEmpty()) {
            Assert.fail("Falharam ${erros.size()} de ${linhasTestTable.size()} cenários de teste: \n${StringUtils.join(erros, "\n")}");
        }
    }


}