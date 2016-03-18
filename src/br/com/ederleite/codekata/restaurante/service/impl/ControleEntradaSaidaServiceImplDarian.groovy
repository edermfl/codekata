package br.com.ederleite.codekata.restaurante.service.impl

import br.com.ederleite.codekata.restaurante.service.IControleEntradaSaidaService

/**
 * Created by dab on 16/03/16.
 */
public class ControleEntradaSaidaServiceImplDarian implements IControleEntradaSaidaService {

    public Integer calcularMaximoPessoasSimultaneamenteNoRestaurante(final List<Integer> pListaEntradas,
                                                                     final List<Integer> pListaSaidas) {
        validateParams(pListaEntradas, pListaSaidas)

        def intersections = [:]
        def size = pListaEntradas.size() - 1

        0.upto(size) { i ->
            0.upto(size) { j ->
                def e11 = i < size ? pListaEntradas.get(i + 1) : null
                validateValues(pListaEntradas.get(i), pListaSaidas.get(i), pListaEntradas.get(j), pListaSaidas.get(j), e11)
                if (i != j && intersects(pListaEntradas.get(i), pListaSaidas.get(i), pListaEntradas.get(j), pListaSaidas.get(j))) {
                    intersections.put(i, intersections.get(i) == null ? 1 : ++intersections.get(i))
                }
            }
        }
        return intersections ? intersections.countBy { k, v -> v }.values().max() : 1
    }

    private boolean intersects(int e1, int s1, int e2, int s2) {
        return (e1 == e2) || (e1 > e2 ? e1 <= s2 : e2 <= s1);
    }

    def validateValues(Integer e1, Integer s1, Integer e2, Integer s2, Integer e11) {
        if (!e1 || !s1 || !e2 || !s2 || e1 >= s1 || e1 == s2 || (e11 && e1 >= e11)) {
            throw new IllegalArgumentException()
        }
    }

    def validateParams(List<Integer> pE, List<Integer> pS) {
        if (!pE || !pS || pE.size() != pS.size() || pE.size() > 5000 || pE.max() > 15000 || pS.max() > 15000 || pE.min() < 1 || pS.min() < 1) {
            throw new IllegalArgumentException()
        }
    }
}
