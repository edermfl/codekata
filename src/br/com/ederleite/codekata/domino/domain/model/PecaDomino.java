package br.com.ederleite.codekata.domino.domain.model;

/**
 * Created by eml on 24/03/16.
 */
public class PecaDomino {
    private Integer ladoA;

    private Integer ladoB;

    public PecaDomino() {

    }

    public PecaDomino(final Integer pLadoA, final Integer pLadoB) {

	ladoA = pLadoA;
	ladoB = pLadoB;
    }

    public Integer getLadoA() {
	return ladoA;
    }

    public Integer getLadoB() {
	return ladoB;
    }

    public void inverterLado() {
	final Integer auxLado = this.ladoA;
	ladoA = ladoB;
	ladoA = auxLado;
    }

    public void setLadoA(final Integer pLadoA) {
	ladoA = pLadoA;
    }

    public void setLadoB(final Integer pLadoB) {
	ladoB = pLadoB;
    }

    @Override public String toString() {
	return "[" + ladoA + "|" + ladoB + "]";
    }
}
