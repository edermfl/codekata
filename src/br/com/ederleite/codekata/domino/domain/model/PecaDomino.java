package br.com.ederleite.codekata.domino.domain.model;

/**
 * Created by eml on 24/03/16.
 */
public class PecaDomino {
    private Integer pontaA;

    private Integer pontaB;

    public PecaDomino() {

    }

    public PecaDomino(final Integer pPontaA, final Integer pPontaB) {

	pontaA = pPontaA;
	pontaB = pPontaB;
    }

    public Integer getPontaA() {
	return pontaA;
    }

    public Integer getPontaB() {
	return pontaB;
    }

    public void inverterLado() {
	final Integer auxLado = this.pontaA;
	pontaA = pontaB;
	pontaA = auxLado;
    }

    public void setPontaA(final Integer pPontaA) {
	pontaA = pPontaA;
    }

    public void setPontaB(final Integer pPontaB) {
	pontaB = pPontaB;
    }

    @Override public String toString() {
	return "[" + pontaA + "|" + pontaB + "]";
    }
}
