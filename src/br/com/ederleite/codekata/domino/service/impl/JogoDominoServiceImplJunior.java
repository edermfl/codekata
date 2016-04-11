package br.com.ederleite.codekata.domino.service.impl;

import br.com.ederleite.codekata.domino.domain.model.PecaDomino;
import br.com.ederleite.codekata.domino.domain.model.Tabuleiro;
import br.com.ederleite.codekata.domino.service.IJogoDominoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junior on 24/03/16.
 */
public class JogoDominoServiceImplJunior implements IJogoDominoService {

    public Tabuleiro jogar(final List<PecaDomino> pPecas) throws IllegalArgumentException {

	Tabuleiro tab = new Tabuleiro();
	List<PecaDomino> listaFinalPecasSobradas = new ArrayList<PecaDomino>();

	if (pPecas == null || pPecas.size() == 0 || pPecas.size() > 100) {
	    throw new IllegalArgumentException("O n�mero de pe�as " + pPecas.size() + " est� diferente do permitido para o jogo");
	}
	List<PecaDomino> pecasEncaixadas = tab.getPecasEncaixadas();

	pecasEncaixadas.add(pPecas.get(0));
	int indicePecasEncaixadas = 0;
	for (int i = 1; i < pPecas.size(); i++) {

	    if (pecasEncaixadas.get(indicePecasEncaixadas).getPontaB().equals(pPecas.get(i).getPontaA())) {
		tab.encaixarPeca(pPecas.get(i));
		indicePecasEncaixadas++;
	    } else {
		if (pPecas.get(i).getPontaB().equals(pecasEncaixadas.get(indicePecasEncaixadas).getPontaB())) {
		    pPecas.get(i).inverterLado();
		    tab.encaixarPeca(pPecas.get(i));
		    indicePecasEncaixadas++;
		} else {
		    tab.adicionarSobra(pPecas.get(i));
		}

	    }

	}

	if (tab.getPecasSobraram().size() > 0) {

	    final List<PecaDomino> pecasSobradas = tab.getPecasSobraram();
	    final List<PecaDomino> tabPecasEncaixadas = tab.getPecasEncaixadas();
	    for (int i = 0; i < pecasSobradas.size(); i++) {

		if (pecasSobradas.get(i).getPontaA().equals(tabPecasEncaixadas.get(0).getPontaA())) {

		    pecasSobradas.get(i).inverterLado();
		    listaFinalPecasSobradas.add(pecasSobradas.get(i));
		    tabPecasEncaixadas.add(0, pecasSobradas.get(i));
		    tab.setPecasEncaixadas(tabPecasEncaixadas);
		    pecasSobradas.remove(i--);
		} else if (pecasSobradas.get(i).getPontaB().equals(tabPecasEncaixadas.get(0).getPontaA())) {

		    listaFinalPecasSobradas.add(pecasSobradas.get(i));
		    tabPecasEncaixadas.add(0, pecasSobradas.get(i));

		    tab.setPecasEncaixadas(tabPecasEncaixadas);
		    pecasSobradas.remove(i--);
		} else {

		    if (!pecasSobradas.contains(pecasSobradas.get(i))) {

			tab.adicionarSobra(pecasSobradas.get(i));
		    }
		}

	    }

	}
	return tab;
    }
}


