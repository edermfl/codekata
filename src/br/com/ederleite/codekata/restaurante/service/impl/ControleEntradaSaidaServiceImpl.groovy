package br.com.ederleite.codekata.restaurante.service.impl

import br.com.ederleite.codekata.restaurante.service.IControleEntradaSaidaService

/**
 * Created by eml on 02/03/16.
 */
public class ControleEntradaSaidaServiceImpl implements IControleEntradaSaidaService {

    @Override
    public Integer calcularMaximoPessoasSimultaneamenteNoRestaurante(final List<Integer> pListaEntradas,
                                                                     final List<Integer> pListaSaidas) throws IllegalArgumentException {
        validarListas(pListaEntradas, pListaSaidas)

        def maximoPessoas = 1;
        pListaSaidas.eachWithIndex { saida, i ->
            def entradaAnterior = 0
            def quant = pListaEntradas.subList(i, pListaEntradas.size()).count { entrada ->
                validarEntradaSaida(entradaAnterior, entrada, saida)
                entradaAnterior = entrada
                saida >= entrada
            }
            maximoPessoas = maximoPessoas < quant ? quant : maximoPessoas
        }
        return maximoPessoas;
    }

    private void validarListas(List<Integer> pListaEntradas, List<Integer> pListaSaidas) {
        def tamanhoEntrada = pListaEntradas.size()
        if (tamanhoEntrada == 0 || tamanhoEntrada != pListaSaidas.size()) {
            throw new IllegalArgumentException("Tamanho das lista não são iguais, ou estão vazias");
        }
        if (tamanhoEntrada > 5000) {
            throw new IllegalArgumentException("Tamanho máximo permitido da lista é 5000");
        }
    }

    private void validarEntradaSaida(int entradaAnterior, int entrada, int saida) {
        if (entrada == saida) {
            throw new IllegalArgumentException("Não é possível entrar ($entrada) e sair ($saida) ao mesmo tempo.");
        }
        if (saida > 15000) {
            throw new IllegalArgumentException("O restaurante já está fechado, saída: $saida.");
        }
        if (entradaAnterior >= entrada) {
            throw new IllegalArgumentException("A Pessoa que entrou antes ($entradaAnterior), não pode ter entrado no mesmo horário ou depois do atual ($entrada).");
        }
    }
}
