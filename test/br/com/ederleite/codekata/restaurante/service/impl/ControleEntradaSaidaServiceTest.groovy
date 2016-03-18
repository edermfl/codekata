package br.com.ederleite.codekata.restaurante.service.impl

import br.com.ederleite.codekata.restaurante.service.IControleEntradaSaidaService
import br.com.ederleite.codekata.util.ConstantsCodekata
import org.apache.commons.lang3.StringUtils
import org.junit.Assert
import org.junit.Test

/**
 * Created by eml on 02/03/16.
 */
public class ControleEntradaSaidaServiceTest {

    private final IControleEntradaSaidaService service = new ControleEntradaSaidaServiceImplEder() ;

    @Test
    public void testEncontrarTestTable() throws IOException {
        // leio o arquivo texto, e transformo-o em uma lista de strings
        final List<String> linhasTestTable = ConstantsCodekata.obterCenariosTestTable(this.getClass());
        // os erro serão adicionados nessa lista
        final List<String> erros = new ArrayList<String>();
        for (String linhaTxt : linhasTestTable) {
            final String[] linha = StringUtils.splitPreserveAllTokens(linhaTxt.substring(1, linhaTxt.length() - 1), '|');

            final String cenario = linha[0];
            final List<Integer> entrada = StringUtils.splitPreserveAllTokens(linha[1], ';').collect { new Integer(it) };
            final List<Integer> saida = StringUtils.splitPreserveAllTokens(linha[2], ';').collect { new Integer(it) };
            def resultadoEsperado = linha[3];

            final boolean erroEsperado = 'ERRO' == resultadoEsperado as String;
            try {
                def quantidade = service.calcularMaximoPessoasSimultaneamenteNoRestaurante(entrada, saida);

                if (erroEsperado) {
                    erros.add("Cenario " + cenario + " falhou: pois um erro era esperado (Dados no cenario: " + linhaTxt + ")");
                    System.out.println("Cenário " + cenario + ": falhou!");
                    continue;
                }
                //converto valor para inteiro
                resultadoEsperado = resultadoEsperado as Integer

                if (resultadoEsperado != quantidade) {
                    erros.add("Cenario $cenario falhou: Esperado ($resultadoEsperado), mas retornado ($quantidade)");
                    System.out.println("Cenário $cenario: falhou!");
                    continue;
                }
            } catch (Throwable e) {
                if (!erroEsperado) {
                    erros.add("Cenario " + cenario + " falhou: pois NAO era esperado erro neste cenario (Dados no cenario: "
                            + linhaTxt + "). Erro lancado: " + e.getMessage());
                    System.out.println("Cenário " + cenario + ": falhou!");
                    continue;
                }
            }
            System.out.println("Cenário " + cenario + ": ok!");
        }

        // se lista de erros contiver algum item, então falho o teste e exibo os erros.
        if (!erros.isEmpty()) {
            Assert.fail("Os Seguintes cenarios de teste falharam: \n" + StringUtils.join(erros, "\n"));
        }
    }

    @Test
    public void testeCalcularMaximoPessoasSimultaneamenteNoRestaurante1() {
        IControleEntradaSaidaService service = new ControleEntradaSaidaServiceImplEder();
        final List<Integer> listaEntradas = Arrays.asList(14, 67, 98);
        final List<Integer> listaSaidas = Arrays.asList(1890, 1900, 2123);
        final Integer quantidade = service.calcularMaximoPessoasSimultaneamenteNoRestaurante(listaEntradas, listaSaidas);
        Assert.assertTrue("Quantidade esperada (3) não foi a retornada (" + quantidade + ")", 3 == quantidade);
    }

    @Test
    public void testeCalcularMaximoPessoasSimultaneamenteNoRestaurante2() {
        IControleEntradaSaidaService service = new ControleEntradaSaidaServiceImplEder();
        final List<Integer> listaEntradas = Arrays.asList(200, 1800);
        final List<Integer> listaSaidas = Arrays.asList(1543, 2324);
        final Integer quantidade = service.calcularMaximoPessoasSimultaneamenteNoRestaurante(listaEntradas, listaSaidas);
        Assert.assertTrue("Quantidade esperada (1) não foi a retornada (" + quantidade + ")", 1 == quantidade);
    }


    @Test
    public void criaTeste() {
        List<Integer> entrada = new ArrayList<Integer>()
        List<Integer> saida = new ArrayList<Integer>()
        for (int i = 1; i < 5000; i++) {
            entrada.add(i * 2)
            saida.add(i * 2 + 5001)
        }
        println("|${entrada.join(";")}|${saida.join(";")}|5000|")
    }


}