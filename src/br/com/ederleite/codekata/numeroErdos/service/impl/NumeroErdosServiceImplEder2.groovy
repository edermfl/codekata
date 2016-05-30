package br.com.ederleite.codekata.numeroErdos.service.impl

import br.com.ederleite.codekata.numeroErdos.service.INumeroErdosService

/**
 * Created by eder on 08/05/2016.
 */
public class NumeroErdosServiceImplEder2 implements INumeroErdosService {

    public static final String ERDOS = 'P. Erdos'

    def autoresJaConsultados = []
    def Map<String, Set<String>> relacionamentoEntreAutores = [:]

    @Override
    public Integer descobrirNumeroErdosDoAutor(
            final String pNomeAutor, final List<String> pAutoresArtigos) throws IllegalArgumentException {

        // se nome procurado for erdos, retorna 0 direto.
        if (pNomeAutor == ERDOS) return 0;


        for (String listaAutoresPorArtigo : pAutoresArtigos) {
            // se na lista de autores tiver erdos e o autor procurado, então retorno 1
            if (listaAutoresPorArtigo.contains(ERDOS) && listaAutoresPorArtigo.contains(pNomeAutor)) {
                return 1;
            }

            def listaAutores = listaAutoresPorArtigo.split(",")*.trim()
            for (String autor : listaAutores) {
                def novosAutores = new HashSet<String>(listaAutores)
                novosAutores.remove(autor)  //removo o próprio autor da lista

                def coautores = relacionamentoEntreAutores.get(autor)
                if (!coautores) {
                    coautores = novosAutores
                } else {
                    coautores.addAll(novosAutores)
                }

                relacionamentoEntreAutores.put(autor, coautores)
            }

        }

        Integer numero = descobrirNumero(pNomeAutor, 1, [])
        relacionamentoEntreAutores.clear()
        return numero

    }

    int descobrirNumero(
            final String pNomeAutorProcurado, int numeroErdos, List<String> pAutoresJaConsultados) {
        def listaCoautores = relacionamentoEntreAutores.get(pNomeAutorProcurado)
        def numeroDoAutorProcurado = numeroErdos
        listaCoautores?.removeAll(pAutoresJaConsultados)
        if (!listaCoautores) {
            return -1
        }
        if (listaCoautores.contains(ERDOS)) {
            return numeroErdos;
        }

        def numeroAnterior = 101
        for (String coautor : listaCoautores) {
            pAutoresJaConsultados.add(pNomeAutorProcurado)
            int retorno = descobrirNumero(coautor, numeroDoAutorProcurado +1, pAutoresJaConsultados)
            if (retorno == 2) return retorno
            if (retorno < numeroAnterior) {
                numeroAnterior = retorno
            }
        }

        return numeroAnterior;


    }
}
