package br.com.ederleite.codekata.domino.service.impl

import br.com.ederleite.codekata.domino.domain.model.PecaDomino
import br.com.ederleite.codekata.domino.domain.model.Tabuleiro
import br.com.ederleite.codekata.domino.service.IJogoDominoService
import org.apache.commons.collections.CollectionUtils

/**
 * Created by eml on 24/03/16.
 */
public class JogoDominoServiceImpl2 implements IJogoDominoService {

    @Override
    public Tabuleiro jogar(final List<PecaDomino> pPecas) throws IllegalArgumentException {
        if (CollectionUtils.isEmpty(pPecas)) throw new IllegalArgumentException("Não posso jogar se a lista está nula ou vazia.")
        if (pPecas.size() > 100) throw new IllegalArgumentException("Máximo permitido são 100 peças a lista possui ${pPecas.size()} peças.")

        //ordena lista... carretões na frente, pois devem ser os primeiros a serem encaixados.
//        pPecas.sort { a, b ->
//            if (a.pontaA == a.pontaB) return -1
//            if (b.pontaA == b.pontaB) return 1
//            return a.pontaA <=> b.pontaA + b.pontaB <=> b.pontaB
//            return 0
//        }

        pPecas.each{  peca ->


        }

        LinkedList<PecaDomino> pecasSequencia = [] as LinkedList;

        // adiciona a primeira peça
        def primeiraPeca = validarPeca(pPecas.remove(0))
        pecasSequencia.add(primeiraPeca)

        //os extremos da sequencia são inicialmente a pontaA e B da primeira peça
        PecaDomino pecaExtremos = new PecaDomino(primeiraPeca.pontaA, primeiraPeca.pontaB)

        while (encaixarProximaPeca(pPecas, pecaExtremos, pecasSequencia)) {
            //continua até não encontrar mais peças que se encaixe.
        }
        def tabuleiro = new Tabuleiro()
        tabuleiro.setPecasEncaixadas(pecasSequencia)
        tabuleiro.setPecasSobraram(pPecas)
        return tabuleiro;
    }

    PecaDomino validarPeca(final PecaDomino peca) {
        if (peca.pontaA == null || peca.pontaB == null || peca.pontaA > 6 || peca.pontaB > 6)
            throw new IllegalArgumentException("Apenas são válidas, peças cuja as pontas possuam valores entre 0 e 6, a peça atual possui o seguinte valor: $peca")
        return peca
    }

    private boolean encaixarProximaPeca(List<PecaDomino> pPecas, PecaDomino pecaExtremos, LinkedList<PecaDomino> pecasSequencia) {
        def iterator = pPecas.iterator()
        while (iterator.hasNext()) {
            PecaDomino peca = validarPeca(iterator.next())
            if (pecaExtremos.pontaB == peca.pontaB) peca.inverterLado()
            if (pecaExtremos.pontaB == peca.pontaA) {
                pecasSequencia.addLast(peca)
                pecaExtremos.pontaB = peca.pontaB
                iterator.remove()
                return true
            }
            if (pecaExtremos.pontaA == peca.pontaA) peca.inverterLado()
            if (pecaExtremos.pontaA == peca.pontaB) {
                pecasSequencia.addFirst(peca)
                pecaExtremos.pontaA = peca.pontaA
                iterator.remove()
                return true
            }

        }
        return false
    }

}