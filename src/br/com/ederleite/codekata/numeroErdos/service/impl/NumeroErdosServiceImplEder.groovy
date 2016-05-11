package br.com.ederleite.codekata.numeroErdos.service.impl

import br.com.ederleite.codekata.numeroErdos.service.INumeroErdosService

/**
 * Created by eder on 08/05/2016.
 */
public class NumeroErdosServiceImplEder implements INumeroErdosService {

    public static final String ERDOS = 'P. Erdos'

    @Override
    public Integer descobrirNumeroErdosDoAutor(
            final String pNomeAutor, final List<String> pAutoresArtigos) throws IllegalArgumentException {
        Map<String, Integer> numeroPorAutor = [ERDOS: 0]
        for (String autores : pAutoresArtigos) {
            def listaAutores = autores.split(",")*.trim()
            def erdos = listaAutores.remove(ERDOS)
            if (erdos) {
                if (listaAutores.contains(pNomeAutor)) {
                    return 1;
                }
                listaAutores.each { autor ->
                    numeroPorAutor.put(autor, 1)
                }
            } else {
                Integer numero;
                def iterator = listaAutores.iterator();
                while (iterator.hasNext()) {
                    def autor = iterator.next()
                    numero = numeroPorAutor.get(autor)
                    if (numero) {
                        if (listaAutores.contains(pNomeAutor)) {
                            return numero + 1;
                        }
                        iterator.remove()
                        break;
                    }
                }
                for (String autor : listaAutores) {
                    if (!numeroPorAutor.containsKey(autor)) {
                        def novoNumero = numero == null ? -1 : numero + 1
                        numeroPorAutor.put(autor, novoNumero)
                    }
                }
            }
        }


        def numeroErdos = numeroPorAutor.get(pNomeAutor)
        return numeroErdos ? numeroErdos : -1;
    }
}
