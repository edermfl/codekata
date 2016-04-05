package br.com.ederleite.codekata.domino.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eml on 24/03/16.
 */
public class Tabuleiro {
    private List<PecaDomino> pecasEncaixadas = new ArrayList<PecaDomino>();

    private List<PecaDomino> pecasSobraram = new ArrayList<PecaDomino>();

    public void adicionarSobra(final PecaDomino pPeca) {
	pecasSobraram.add(pPeca);
    }

    public void encaixarPeca(final PecaDomino pPeca) {
	pecasEncaixadas.add(pPeca);
    }

    public List<PecaDomino> getPecasEncaixadas() {
	return pecasEncaixadas;
    }

    public List<PecaDomino> getPecasSobraram() {
	return pecasSobraram;
    }

    public void setPecasEncaixadas(final List<PecaDomino> pPecasEncaixadas) {
	pecasEncaixadas = pPecasEncaixadas;
    }

    public void setPecasSobraram(final List<PecaDomino> pPecasSobraram) {
	pecasSobraram = pPecasSobraram;
    }

    @Override public String toString() {
	return "  " + pecasEncaixadas.size() + " Peças encaixadas: " + StringUtils.join(pecasEncaixadas, "") +
			"\n  " + pecasSobraram.size() + " Peças sobraram=" + (pecasSobraram.isEmpty() ?
			" Sem sobras" :
			StringUtils.join(pecasSobraram, ""));
    }
}
