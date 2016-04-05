package br.com.ederleite.codekata.domino.service.impl

import br.com.ederleite.codekata.domino.domain.model.PecaDomino
import br.com.ederleite.codekata.domino.domain.model.Tabuleiro
import br.com.ederleite.codekata.domino.service.IJogoDominoService
import org.apache.commons.collections.CollectionUtils

/**
 * Created by eml on 24/03/16.
 */
public class JogoDominoServiceImpl implements IJogoDominoService {

    @Override
    public Tabuleiro jogar(final List<PecaDomino> pPecas) throws IllegalArgumentException {
        if (CollectionUtils.isEmpty(pPecas)) {
            throw new IllegalArgumentException("Não posso jogar se a lista está nula ou vazia.")
        }
        LinkedList<PecaDomino> pecasSequencia = [] as LinkedList;
        List<PecaDomino> sobras = []
        PecaDomino pecaExtremos = new PecaDomino()
        def iteratorPecas = pPecas.iterator()
        while (iteratorPecas.hasNext()) {
            def proximaPeca = iteratorPecas.next()

            //adiciona primeira peça direto
            if (pecasSequencia.isEmpty()) {
                pecasSequencia.add(proximaPeca)
                pecaExtremos.setPontaA(proximaPeca.pontaA)
                pecaExtremos.setPontaB(proximaPeca.pontaB)
                continue;
            }

            /** 1º tanta encaixar de peças na pontaB da sequencia, ou seja, falado direito (fim)*/
            // pontaB da próxima peça encaixa com o extremo pontaB da sequencia?
            if (proximaPeca.pontaB == pecaExtremos.pontaB) {
                proximaPeca.inverterLado()    // inverto a peça
            }
            // pontaA da próxima peça encaixa com o extremo pontaB da sequencia?
            if (proximaPeca.pontaA == pecaExtremos.pontaB) {
                pecasSequencia.addLast(proximaPeca)
                pecaExtremos.setPontaB(proximaPeca.pontaB)
                continue;
            }

            /** 2º tanta encaixar de peças na pontaA da sequencia, ou seja, falado esquerdo (inicio)*/
            // pontaA da próxima peça encaixa com o extremo pontaA da sequencia?
            if (proximaPeca.pontaA == pecaExtremos.pontaA) {
                proximaPeca.inverterLado()    // inverto a peça
            }
            // pontaB da próxima peça encaixa com o extremo pontaA da sequencia?
            if (proximaPeca.pontaB == pecaExtremos.pontaA) {
                pecasSequencia.addFirst(proximaPeca)
                pecaExtremos.setPontaA(proximaPeca.pontaA)
                continue;
            }

            /** 3º não obteve encaixe, então sobrou */
            sobras.add(proximaPeca)
        }
        def tabuleiro = new Tabuleiro()
        tabuleiro.setPecasEncaixadas(pecasSequencia)
        tabuleiro.setPecasSobraram(sobras)
        return tabuleiro;
    }

}
