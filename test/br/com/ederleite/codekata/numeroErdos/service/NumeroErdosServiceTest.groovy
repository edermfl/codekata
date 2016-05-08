package br.com.ederleite.codekata.numeroErdos.service

import br.com.ederleite.codekata.domino.domain.model.PecaDomino
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
        // os erro serão adicionados nessa lista
        final List<String> erros = new ArrayList<String>();
        for (String linhaTxt : linhasTestTable) {
            final String[] linha = StringUtils.splitPreserveAllTokens(linhaTxt.substring(1, linhaTxt.length() - 1), '|');

            //|cenario|nomeAutor|arquivoListaArtigos|retorno|ok|
            final String cenario = linha[0];
            final String nomeAutor = linha[1]
            final nomeArquivo = "${linha[2]}.artigo"
            final List<String> listaAutoresPorArtigos = CodekataUtil.obterLinhasDoArquivo(this.class, nomeArquivo)
            final Integer retorno = linha[3] as Integer
            def resultadoEsperado = linha[4];

            final boolean erroEsperado = 'erro' == resultadoEsperado || 'ERRO' == resultadoEsperado;
            Integer numeroErdos
            def mensagemFalha = "Cenário $cenario: falhou! \nEntrada: ${pecasDomino ? StringUtils.join(pecasDomino, "") : "entrada nula"} \nSaida:\n"
            try {
                numeroErdos = service.descobrirNumeroErdosDoAutor(nomeAutor, listaAutoresPorArtigos);

                if (erroEsperado) {
                    erros.add("Cenario " + cenario + " falhou: pois um erro era esperado (Dados no cenario: " + linhaTxt + ")");
                    System.out.println(mensagemFalha + numeroErdos);
                    continue;
                }

                def quantRetornada = tabuleiro.getPecasEncaixadas().size()
                if (quantEncaixadas != quantRetornada) {
                    erros.add("Cenario $cenario falhou: Esperado $quantEncaixadas encaixadas, mas retornado ${quantRetornada}");
                    System.out.println(mensagemFalha + tabuleiro);
                    continue;
                }

                quantRetornada = tabuleiro.getPecasSobraram().size()
                if (quantSobras != quantRetornada) {
                    erros.add("Cenario $cenario falhou: Esperado $quantSobras sobras, mas retornado ${quantRetornada}");
                    System.out.println(mensagemFalha + tabuleiro);
                    continue;
                }

                PecaDomino pecaAnterior = new PecaDomino();
                tabuleiro.getPecasEncaixadas().eachWithIndex { PecaDomino pecaAtual, i ->
                    // ponta A da próxima peça é igual a ponta B da peça anterior
                    if (pecaAnterior.pontaB == null || pecaAtual.pontaA == pecaAnterior.pontaB) {
                        pecaAnterior = pecaAtual
                    } else {
                        erros.add("Cenario $cenario falhou: Na ${i}º peça encaixada, a pontaB ${pecaAnterior.pontaB} não se encaixa na pontaA ${pecaAtual.pontaA} da próxima peça. Segue jogo: ${tabuleiro.getPecasEncaixadas()}");
                        System.out.println(mensagemFalha + tabuleiro);
                    }
                }

            } catch (Throwable e) {
                if (!erroEsperado) {
                    erros.add("Cenario " + cenario + " falhou: pois NAO era esperado erro neste cenario (Dados no cenario: "
                            + linhaTxt + "). Jogo: ${tabuleiro}, Erro lancado: " + e.getMessage());
                    System.out.println(mensagemFalha + tabuleiro);
                    continue;
                }
            }
            System.out.println("Cenário " + cenario + ": ok! " + (tabuleiro != null ? "Saida:\n" + tabuleiro : ""));
        }

        // se lista de erros contiver algum item, então falho o teste e exibo os erros.
        if (!erros.isEmpty()) {
            Assert.fail("Os Seguintes cenarios de teste falharam: \n${StringUtils.join(erros, "\n")}");
        }
    }


}