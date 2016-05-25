package br.com.ederleite.codekata.numeroErdos.service.impl

import br.com.ederleite.codekata.numeroErdos.service.INumeroErdosService

/**
 * Created by eder on 08/05/2016.
 */
public class NumeroErdosServiceImplEderGrafo implements INumeroErdosService {

    public static final String ERDOS = 'P. Erdos'

    @Override
    public Integer descobrirNumeroErdosDoAutor(
            final String pNomeAutor, final List<String> pAutoresArtigos) throws IllegalArgumentException {

        if (pNomeAutor == ERDOS) return 0;
        Map<String, NoAutor> mapaNohAutores = [:]
        mapaNohAutores.put(ERDOS, new NoAutor(ERDOS));
        for (String autores : pAutoresArtigos) {
            def listaAutores = autores.split(",")*.trim()
            //Erdos está na lista de autores
            def erdos = listaAutores.remove(ERDOS)
            if (erdos) {
                //autor procurado, escreveu com Edros, já retorno 1
                if (listaAutores.contains(pNomeAutor)) {
                    return 1;
                }
                //se não, todos que escreveram com Erdos vão pro mapa de nós com ligação direta para o nó de Erdos
                listaAutores.each { String autor ->
                    mapaNohAutores.put(autor, new NoAutor(autor, mapaNohAutores.get(ERDOS)))
                }
            }
            // Erdos não está na lista de autores
            else {
                NoAutor autorJaMapeado = obterAutorJaMapeado(listaAutores, mapaNohAutores)
                if (autorJaMapeado && listaAutores.contains(pNomeAutor)) {
                    return autorJaMapeado.obterNumero() + 1
                }
                //regitrar autores não mapeados
                for (String autorNaoMapeado : listaAutores) {
                    if (!mapaNohAutores.containsKey(autorNaoMapeado)) {
                        mapaNohAutores.put(autorNaoMapeado, new NoAutor(autorNaoMapeado, autorJaMapeado))
                    }
                }
            }
        }
        return mapaNohAutores.get(pNomeAutor).obterNumero()
    }

    private NoAutor obterAutorJaMapeado(ArrayList<String> listaAutores, LinkedHashMap<String, NoAutor> mapaNohAutores) {
        for (String autor : listaAutores) {
            def autorJahMapeado = mapaNohAutores.get(autor)
            if (autorJahMapeado) {
                listaAutores.remove(autorJahMapeado.nome)
                return autorJahMapeado;
            }
        }
    }

    private class NoAutor {
        NoAutor anterior
        String nome

        NoAutor(final String pNome, final NoAutor pAnterior) {
            anterior = pAnterior
            nome = pNome
        }

        NoAutor(final String pNome) {
            nome = pNome
        }

        def obterNumero() {
            def numero = 0
            def noh = anterior
            while (noh != null) {
                numero++;
                noh = noh.anterior
            }
            return numero == 0 && nome != ERDOS ? -1 : numero;
        }
    }
}
