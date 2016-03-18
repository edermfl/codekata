package br.com.ederleite.codekata.restaurante.service.impl;

import br.com.ederleite.codekata.restaurante.service.IControleEntradaSaidaService;

import java.util.ArrayList;
import java.util.List;

/**
* Created by dea on 07/03/16.
*/
public class ControleEntradaSaidaServiceImplDebby implements IControleEntradaSaidaService {

    private List<Integer> pessoasJaComputadas = new ArrayList<Integer>();

    public Integer calcularMaximoPessoasSimultaneamenteNoRestaurante(final List<Integer> pListaEntradas,
                  final List<Integer> pListaSaidas) throws IllegalArgumentException {
       if (pListaEntradas.size() != pListaSaidas.size()) {
           System.out.println("Não foi possível calcular, as listas devem ser do mesmo tamanho!");
           throw new IllegalArgumentException();
       }
       if (pListaEntradas.size() > 5000) {
           System.out.println("Não foi possível calcular, as listas não devem ser maior que 5000.");
           throw new IllegalArgumentException();
       }

       int pessoasSimultaneas = 1;
       for (int i = 0; i < pListaEntradas.size() - 1; i++) {

           if (pListaEntradas.get(i) == null || pListaSaidas.get(i) == null) {
              System.out.println("Não foi possível calcular, o tempo não pode ser nulo1!");
              throw new IllegalArgumentException();
           } else if (pListaEntradas.get(i) < 1 || pListaSaidas.get(i) < 1) {
              System.out.println("Não foi possível calcular, o tempo não pode ser menor que 1!");
              throw new IllegalArgumentException();
           } else if (pListaEntradas.get(i) > 15000 || pListaSaidas.get(i) > 15000) {
              System.out.println("Não foi possível calcular, o tempo ultrapassou o limite de 15000!");
              throw new IllegalArgumentException();
           }
           if (pListaEntradas.get(i) == pListaEntradas.get(i + 1) || pListaSaidas.get(i) == pListaSaidas.get(i + 1)) {
              System.out.println("Ocorreu erro no sistema! Não é possível duas pessoas entrarem ou sairem ao mesmo tempo!");
              throw new IllegalArgumentException();
           }

           pessoasSimultaneas = getPessoasSimultaneas(pListaEntradas, pListaSaidas, pessoasSimultaneas, i);
       }

       return pessoasSimultaneas;
    }

    private int getPessoasSimultaneas(final List<Integer> pListaEntradas, final List<Integer> pListaSaidas,
                  int pPessoasSimultaneas, final int pProximoAValidar) {

       if (pessoasJaComputadas.contains(pListaEntradas.get(pProximoAValidar))) {
           return pPessoasSimultaneas;
       }
       pessoasJaComputadas.add(pListaEntradas.get(pProximoAValidar));
       for (int i = pProximoAValidar + 1; i < pListaEntradas.size(); i++) {
           if (pessoasJaComputadas.contains(pListaEntradas.get(i))) {
              continue;
           }
           final boolean entradaDoProximoAntesDeste =
                         pListaEntradas.get(i) < pListaSaidas.get(pProximoAValidar);
           final boolean entradaDesteAntesSaidaProximo =
                         pListaEntradas.get(pProximoAValidar) < pListaSaidas.get(i);

           if (entradaDoProximoAntesDeste && entradaDesteAntesSaidaProximo) {
              if (!pessoasJaComputadas.contains(pListaEntradas.get(pProximoAValidar))) {
                  pPessoasSimultaneas = pPessoasSimultaneas + 1;
              }
              pessoasJaComputadas.add(pListaEntradas.get(i));
              pPessoasSimultaneas = pPessoasSimultaneas + 1;
           }
       }
       return pPessoasSimultaneas;
    }
}