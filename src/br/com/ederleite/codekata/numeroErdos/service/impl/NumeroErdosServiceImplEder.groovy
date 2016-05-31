package br.com.ederleite.codekata.numeroErdos.service.impl

import br.com.ederleite.codekata.numeroErdos.service.INumeroErdosService

/**
 * Created by eder on 08/05/2016.
 */
public class NumeroErdosServiceImplEder implements INumeroErdosService {

    public static final String ERDOS = 'P. Erdos'

    def Map<String, Set<String>> relacionamentoEntreAutores = [:]

    @Override
    public Integer descobrirNumeroErdosDoAutor(
            final String pNomeAutor, final List<String> pAutoresArtigos) throws IllegalArgumentException {

        validarNomeAutor(pNomeAutor)
        validarListaArtigos(pAutoresArtigos)

        // se nome procurado for erdos, retorna 0 direto.
        if (pNomeAutor == ERDOS) return 0;

        for (String listaAutoresPorArtigo : pAutoresArtigos) {
            def listaAutores = listaAutoresPorArtigo.split(",")*.trim()
            validarListaAutores(listaAutores)
            for (String autor : listaAutores) {
                validarNomeAutor(autor)

                // se na lista de autores tiver erdos e o autor procurado, então retorno 1
                if (listaAutores.contains(ERDOS) && listaAutores.contains(pNomeAutor)) {
                    return 1;
                }

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

    private void validarListaAutores(final List<String> pListaAutoresPorArtigo) throws IllegalArgumentException {
        if (pListaAutoresPorArtigo.size() > 10) {
            throw new IllegalArgumentException("Erro");

        }
    }

    private void validarListaArtigos(final List<String> pArtigos) throws IllegalArgumentException {
        if (!pArtigos || pArtigos.size() > 100) {
            throw new IllegalArgumentException("Erro");
        }
    }

    private void validarNomeAutor(final String pNomeAutor) throws IllegalArgumentException {
        if (!pNomeAutor || pNomeAutor.length() > 15 || pNomeAutor.count(' ') > 1) {
            throw new IllegalArgumentException("Erro");
        }
    }

    int descobrirNumero(
            final String pNomeAutorProcurado, int numeroErdos, List<String> pAutoresJaConsultados) {
        def listaCoautores = relacionamentoEntreAutores.get(pNomeAutorProcurado)
        listaCoautores?.removeAll(pAutoresJaConsultados)
        if (!listaCoautores) {
            return -1
        }
        if (listaCoautores.contains(ERDOS)) {
            return numeroErdos;
        }

        def numeroEncontrados = []
        for (String coautor : listaCoautores) {
            pAutoresJaConsultados.add(pNomeAutorProcurado)

            def numero = descobrirNumero(coautor, numeroErdos + 1, pAutoresJaConsultados)
            numeroEncontrados << numero

        }

        if (numeroEncontrados.size() > 1) numeroEncontrados = numeroEncontrados.findAll { it != -1 }
        return numeroEncontrados ? numeroEncontrados?.min() : -1;
    }
}
