package br.com.ederleite.example.codekata.encontreSequencia.service.impl;

import br.com.ederleite.example.codekata.encontreSequencia.domain.model.PosicaoTO;
import br.com.ederleite.example.codekata.encontreSequencia.service.IEncontrarSequencia;

public class EncontrarSequenciaRafael extends PosicaoTO implements IEncontrarSequencia {
    public PosicaoTO encontrar(final String p, final String t) {
	PosicaoTO pto = new PosicaoTO();
        char[] cadeiaPequena = p.toCharArray();
        char[] cadeiaPrincipal = t.toCharArray();
        char[] cadeiaPequenaInvertida = new char[cadeiaPequena.length];
        int guardarPosicao = 0;
        int aux = 0;
        //Invertendo a string menor
        for (int i = (cadeiaPequena.length - 1); i >= 0; i--) {
            cadeiaPequenaInvertida[aux] = cadeiaPequena[i];
            aux++;
        }
        //Esse for irá percorrer toda a string maior (T)
        for (int i = 0; i < cadeiaPrincipal.length; i++) {
            //Caso as próximas letras de P não for iguais em T, reseto a variável que guardei a posição.
            guardarPosicao = 0;
            //Encontrar a primeira letra da string menor (direta ou invertida) na string maior(T)
            if ((cadeiaPequena[0] == cadeiaPrincipal[i]) || (cadeiaPequenaInvertida[0] == cadeiaPrincipal[i])) {
                //Caso a primeira letra for encontrada vou guardar a posição para posteriormente adicionar na minha lista(Direta ou Inversa)
                guardarPosicao = i;
                //Esse if eu verifico se a string menor possui apenas uma posição, caso isso for verdade eu já adiciono as possições, pois não existira posições posteriores a serem verificadas.
                if (cadeiaPequena.length == 1) {
                    pto.addPosicaoReversa(guardarPosicao+1);
                    pto.addPosicaoDireta(guardarPosicao+1);
                }
                //Esse for vai percorrer nas proximas posições das strings menores (Direta ou Invesa)
                for (int j = 1; j < cadeiaPequena.length; j++) {
                    //Uma verificação básica para não ocorrer IndexOfBoundException
                    if ((j + i) < cadeiaPrincipal.length) {
                        //Começo a validar se as próximas posições da string menor (Direta) também são iguais as próximas posições da string maior(T)
                        if (cadeiaPequena[j] == cadeiaPrincipal[i + j]) {
                            //Verifica se toda a string menor (Direta) foi percorrida. Se chegar nesse ponto e retorno do if for TRUE, podemos afirmar que P foi encontrado em T
                            if ((j + 1) == cadeiaPequena.length) {
                                // Armazena a posição na lista
                                pto.addPosicaoDireta(guardarPosicao+1);
                            }
                            continue;
                        }
                        //Começo a validar se as próximas posições da string menor (Invertida) também são iguais as próximas posições da string maior(T)
                        if (cadeiaPequenaInvertida[j] == cadeiaPrincipal[i + j]) {
                            //Verifica se toda a string menor (Invertida) foi percorrida. Se chegar nesse ponto e retorno do if for TRUE, podemos afirmar que P foi encontrado em T
                            if ((j + 1) == cadeiaPequenaInvertida.length) {
                                // Armazena a posição na lista
                                pto.addPosicaoReversa(guardarPosicao+1);
                            }
                            continue;
                        }
                    }
                    break;
                }
            }
        }
        return pto;
    }
}