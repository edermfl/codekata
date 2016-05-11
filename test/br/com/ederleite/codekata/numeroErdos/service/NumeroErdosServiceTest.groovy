package br.com.ederleite.codekata.numeroErdos.service

import br.com.ederleite.codekata.numeroErdos.service.impl.NumeroErdosServiceImplEder
import br.com.ederleite.codekata.util.CodekataUtil
import org.apache.commons.lang3.StringUtils
import org.junit.Assert
import org.junit.Test
/**
 * Created by eder on 08/05/2016.
 */
public class NumeroErdosServiceTest {
    private final INumeroErdosService service = new NumeroErdosServiceImplEder();

    @Test
    public void testNumeroErdosTestTable() throws IOException {
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
            final Integer esperado = linha[3] as Integer
            def resultadoEsperado = linha[4];

            final boolean erroEsperado = 'erro' == resultadoEsperado || 'ERRO' == resultadoEsperado;
            Integer numeroErdos
            def mensagemFalha = "\n - NomeAutor: $nomeAutor \n - Artigos:\n       -> ${listaAutoresPorArtigos.join("\n       -> ")}  \n - Saida: $numeroErdos\n"
            try {
                numeroErdos = service.descobrirNumeroErdosDoAutor(nomeAutor, listaAutoresPorArtigos);

                if (erroEsperado) {
                    erros.add("Cenario " + cenario + " falhou: pois um erro era esperado " + mensagemFalha);
                    continue;
                }

                if (esperado != numeroErdos) {
                    erros.add("Cenario $cenario falhou: Esperado $esperado, mas retornado $numeroErdos" + mensagemFalha);
                    continue;
                }

            } catch (Throwable e) {
                if (!erroEsperado) {
                    erros.add("Cenario $cenario falhou: pois NAO era esperado erro neste cenario: ${e.getMessage()}" + mensagemFalha);
                    continue;
                }
            }
            System.out.println("Cenario " + cenario + ": ok! ");
        }

        // se lista de erros contiver algum item, ent�o falho o teste e exibo os erros.
        if (!erros.isEmpty()) {
            Assert.fail("Os Seguintes cenarios de teste falharam: \n${StringUtils.join(erros, "\n")}");
        }
    }


}