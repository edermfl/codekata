package br.com.ederleite.codekata.domino.service
import br.com.ederleite.codekata.domino.domain.model.PecaDomino
import br.com.ederleite.codekata.domino.domain.model.Tabuleiro
import br.com.ederleite.codekata.domino.service.impl.JogoDominoServiceImplEder
import br.com.ederleite.codekata.util.ConstantsCodekata
import org.apache.commons.lang3.StringUtils
import org.junit.Assert
import org.junit.Test
/**
 * Created by eml on 24/03/16.
 */
public class JogoDominoServiceTest {

    private final IJogoDominoService service = new JogoDominoServiceImplEder();

    @Test
    public void testJogoDominioTestTable() throws IOException {
        // leio o arquivo texto, e transformo-o em uma lista de strings
        final List<String> linhasTestTable = ConstantsCodekata.obterCenariosTestTable(this.getClass());
        // os erro serão adicionados nessa lista
        final List<String> erros = new ArrayList<String>();
        for (String linhaTxt : linhasTestTable) {
            final String[] linha = StringUtils.splitPreserveAllTokens(linhaTxt.substring(1, linhaTxt.length() - 1), '|');

            //|cenario|pecas|encaixadas|sobras|
            final String cenario = linha[0];
            final List<PecaDomino> pecasDomino = linha[1] == 'null' ? null : StringUtils.splitPreserveAllTokens(linha[1], ';').collect {
                String peca = StringUtils.isNotBlank(it) ? it : "  "
                return new PecaDomino((StringUtils.isNotBlank(peca[0]) ? peca[0] : null) as Integer, (StringUtils.isNotBlank(peca[1]) ? peca[1] : null) as Integer)
            };
            final Integer quantEncaixadas = linha[2] as Integer
            final Integer quantSobras = linha[3] as Integer
            def resultadoEsperado = linha[4];

            final boolean erroEsperado = 'erro' == resultadoEsperado || 'ERRO' == resultadoEsperado;
            Tabuleiro tabuleiro

            def mensagemFalha = "Cenário $cenario: falhou! \nEntrada: ${pecasDomino ? StringUtils.join(pecasDomino, "") : "entrada nula"} \nSaida:\n"
            try {
                tabuleiro = service.jogar(pecasDomino);

                if (erroEsperado) {
                    erros.add("Cenario " + cenario + " falhou: pois um erro era esperado (Dados no cenario: " + linhaTxt + ")");
                    System.out.println(mensagemFalha + tabuleiro);
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