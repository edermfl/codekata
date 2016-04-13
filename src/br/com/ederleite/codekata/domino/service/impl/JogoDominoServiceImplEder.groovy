package br.com.ederleite.codekata.domino.service.impl

import br.com.ederleite.codekata.domino.domain.model.PecaDomino
import br.com.ederleite.codekata.domino.domain.model.Tabuleiro
import br.com.ederleite.codekata.domino.service.IJogoDominoService
import org.apache.commons.collections.CollectionUtils

/**
 * Created by eml on 24/03/16.
 */
public class JogoDominoServiceImplEder implements IJogoDominoService {

    @Override
    public Tabuleiro jogar(final List<PecaDomino> pPecas) throws IllegalArgumentException {
        return jogarInternal(pPecas, 0)
    }

    private Tabuleiro jogarInternal(List<PecaDomino> pPecas, int pQuantPecaEncaixadaUltimoJogo) {
        if (CollectionUtils.isEmpty(pPecas)) throw new IllegalArgumentException("Não posso jogar se a lista está nula ou vazia.")
        if (pPecas.size() > 100) throw new IllegalArgumentException("Máximo permitido são 100 peças a lista possui ${pPecas.size()} peças.")

        List<PecaDomino> pecasSequencia = [];

        // adiciona a primeira peça
        def primeiraPeca = validarPeca(pPecas.remove(0))
        pecasSequencia.add(primeiraPeca)

        //os extremos da sequencia são inicialmente a pontaA e B da primeira peça
        PecaDomino pecaExtremos = new PecaDomino(primeiraPeca.pontaA, primeiraPeca.pontaB)

        // 1) tenta colocar em sequencias as peças na ordem que ela vem.
        // Nesta abordagem algumas peças que podem ser encaixadas, ficaram sobrando, principalmente carretões.
        while (encaixarProximaPeca(pPecas, pecaExtremos, pecasSequencia, 0)) {
            println(pecasSequencia)
            //continua até não encontrar mais peças que se encaixe.
        }

        // 2) as peças que sobraram, preciso ter certeza que não se encaixam, assim, tento encaixa-las uma a uma
        encaixarPecasSobraram(pPecas, pecasSequencia)

        def tabuleiro = new Tabuleiro()
        tabuleiro.setPecasEncaixadas(pecasSequencia)
        tabuleiro.setPecasSobraram(pPecas)

        // 3) se sobrou mais peças que o as encaixadas, pode ser que comecei com a peça errada. Talvez deva tentar de outra forma.
        def quantPecas = pecasSequencia.size()
        def quantPecaEncaixadasUltimoJogoIgualEsteJogo = pQuantPecaEncaixadaUltimoJogo == 0 || pQuantPecaEncaixadaUltimoJogo < quantPecas
        if (pPecas.size() > quantPecas && !temMuitosCarretoesSobrando(pPecas) && quantPecaEncaixadasUltimoJogoIgualEsteJogo) {
            List<PecaDomino> pecasNovaTentativa = pPecas.reverse() + pecasSequencia

            def tabuleiroNovaTentativa = jogarInternal(pecasNovaTentativa, quantPecas)
            return tabuleiro.getPecasEncaixadas().size() > tabuleiroNovaTentativa.getPecasEncaixadas().size() ? tabuleiro : tabuleiroNovaTentativa

        }

        return tabuleiro;
    }

    boolean temMuitosCarretoesSobrando(final List<PecaDomino> pPecaSobraram) {
        def quantCarretoes = 0;
        for (PecaDomino sobra : pPecaSobraram) {
            quantCarretoes += sobra.pontaA == sobra.pontaB ? 1 : 0
        }
        def quantPecasNormais = pPecaSobraram.size() - quantCarretoes
        return quantPecasNormais < quantCarretoes
    }

    private void encaixarPecasSobraram(List<PecaDomino> pPecas, ArrayList<PecaDomino> pecasSequencia) {
        if (!pPecas.isEmpty()) {
            Iterator<PecaDomino> iterator = pPecas.iterator()
            while (iterator.hasNext()) {
                def pecaAEncaixar = iterator.next()
                for (int i = 0; i < pecasSequencia.size() - 1; i++) {
                    def pecaAntes = pecasSequencia[i]
                    def pecapecaDepois = pecasSequencia[i + 1]
                    //Antes=[5|4]  Depois=[4|2]  À Encaixar [4|4] => OK
                    //Antes=[5|4]  Depois=[4|2]  À Encaixar [6|4] => ERRO
                    if (pecaAntes.pontaB == pecaAEncaixar.pontaB && pecaAEncaixar.pontaA == pecapecaDepois.pontaA) pecaAEncaixar.inverterLado()
                    if (pecaAntes.pontaB == pecaAEncaixar.pontaA && pecaAEncaixar.pontaB == pecapecaDepois.pontaA) {
                        pecasSequencia.add(i + 1, pecaAEncaixar)
                        iterator.remove()
                        println(pecasSequencia)
                        break;
                    }
                }
            }
        }
    }

    PecaDomino validarPeca(final PecaDomino peca) {
        if (peca.pontaA == null || peca.pontaB == null || peca.pontaA > 6 || peca.pontaB > 6)
            throw new IllegalArgumentException("Apenas são válidas, peças cuja as pontas possuam valores entre 0 e 6, a peça atual possui o seguinte valor: $peca")
        return peca
    }

    private boolean encaixarProximaPeca(List<PecaDomino> pPecas, PecaDomino pecaExtremos, List<PecaDomino> pecasSequencia, int tentativas) {
        def i = 0
        Iterator<PecaDomino> iterator = pPecas.iterator()
        while (iterator.hasNext()) {
            PecaDomino peca = validarPeca(iterator.next())
            if (pecaExtremos.pontaB == peca.pontaB) peca.inverterLado()
            if (pecaExtremos.pontaB == peca.pontaA) {
                pecasSequencia.add(peca)
                pecaExtremos.pontaB = peca.pontaB
                iterator.remove()
                print "($i) "
                return true
            }
            if (pecaExtremos.pontaA == peca.pontaA) peca.inverterLado()
            if (pecaExtremos.pontaA == peca.pontaB) {
                pecasSequencia.add(0, peca)
                pecaExtremos.pontaA = peca.pontaA
                iterator.remove()
                print "($i) "
                return true
            }
            i++
        }
        if (!pPecas.isEmpty() && ehCarretao(pecaExtremos) && pecasSequencia.size() > 1 && tentativas <3) {
            //quando o jogo fechar, movo a primeira peça para o fim da sequencia, e recomeça a procura.
            println "Fechou jogo, ${++tentativas}° tentativa de abrir o jogo."
            def primeriaPeca = pecasSequencia.remove(0)
            pecasSequencia.add(primeriaPeca)
            pecaExtremos.pontaA = pecasSequencia.get(0).pontaA
            pecaExtremos.pontaB = primeriaPeca.pontaB
            if(ehCarretao(primeriaPeca) && ehCarretao(pecasSequencia.get(0))){
                return false;
            }
            return encaixarProximaPeca(pPecas, pecaExtremos, pecasSequencia, tentativas)
        }
        return false
    }

    private boolean ehCarretao(PecaDomino peca) {
        peca.pontaA == peca.pontaB
    }

}