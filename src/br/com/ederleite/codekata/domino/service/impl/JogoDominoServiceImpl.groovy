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
        pPecas.each { proximaPeca ->
            //adiciona primeira peça direto
            if (pecasSequencia.isEmpty()) {
                pecasSequencia.add(proximaPeca)
                pecaExtremos.setPontaA(proximaPeca.pontaA)
                pecaExtremos.setPontaB(proximaPeca.pontaB)
                return;
            }

            // pontaA da próxima peça encaixa com o extremo pontaB da sequencia?
            if (proximaPeca.pontaA == pecaExtremos.pontaB) {
                pecasSequencia.add(proximaPeca)
                pecaExtremos.setPontaB(proximaPeca.pontaB)
                return;
            }
            // pontaB da próxima peça encaixa com o extremo pontaB da sequencia?
            else if (proximaPeca.pontaB == pecaExtremos.pontaB) {
                proximaPeca.inverterLado()
                pecasSequencia.add(proximaPeca)
                pecaExtremos.setPontaB(proximaPeca.pontaB)
                return;
            }
            sobras.add(proximaPeca)
        }
        def tabuleiro = new Tabuleiro()
        tabuleiro.setPecasEncaixadas(pecasSequencia)
        tabuleiro.setPecasSobraram(sobras)
        return tabuleiro;
    }

}
