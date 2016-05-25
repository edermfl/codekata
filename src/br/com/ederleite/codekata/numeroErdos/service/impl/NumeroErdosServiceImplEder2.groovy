package br.com.ederleite.codekata.numeroErdos.service.impl

import br.com.ederleite.codekata.numeroErdos.service.INumeroErdosService

/**
 * Created by eder on 08/05/2016.
 */
public class NumeroErdosServiceImplEder2 implements INumeroErdosService {

    public static final String ERDOS = 'P. Erdos'

    @Override
    public Integer descobrirNumeroErdosDoAutor(
            final String pNomeAutor, final List<String> pAutoresArtigos) throws IllegalArgumentException {
        if (pNomeAutor == ERDOS) return 0;
        Map<String, Integer> numeroPorAutor = [ERDOS: 0]

        List<String> listaAutoresDoArtigo = encontrarArtigoDoAutorProcuradoNalista(pNomeAutor, pAutoresArtigos)
        if(!listaAutoresDoArtigo) return -1;
        if(listaAutoresDoArtigo.contains(ERDOS)) return 1;

//       descobreNumero(listaAutoresDoArtigo, pAutoresArtigos, 1)
//
//
//        def numeroErdos = numeroPorAutor.get(pNomeAutor)
//        return numeroErdos ? numeroErdos : -1;
    }

    List<String> encontrarArtigoDoAutorProcuradoNalista(final String pNomeAutor, final List<String> pListaArtigos) {
        def artigo = pListaArtigos.find { it.contains(pNomeAutor) }
        pListaArtigos.remove(artigo);
        def listaAutoresDoArtigo  = []
        if(artigo) {
            listaAutoresDoArtigo = artigo.split(",")*.trim()
            //removo o nome do autor encontrado, pois agora terei que procurar se os autores que escrever com ele, escreveram com ERDOS
            listaAutoresDoArtigo.remove(pNomeAutor)
        }
        return listaAutoresDoArtigo
    }

    Integer descobreNumero(final List<String> pListaAutoresEncontrados, final ArrayList<String> pAutoresArtigos, final int numeroErdos) {

        for (String autor : pListaAutoresEncontrados) {
            List<String> listaAutoresDoArtigo = encontrarArtigoDoAutorProcuradoNalista(autor, pAutoresArtigos)


        }
    }
}
