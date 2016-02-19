package br.com.ederleite.codekata.caixaEletronico.domain.model;

import java.math.BigDecimal;

/**
 * Created by eder on 12/02/2016.
 */
public class QuantidadeNotaTO {
    private Integer notas10;

    private Integer notas2;

    private Integer notas20;

    private Integer notas5;

    private Integer notas50;

    public QuantidadeNotaTO(final Integer pQuantidadeNotas2, final Integer pQuantidadeNotas5, final Integer pQuantidadeNotas10,
		    final Integer pQuantidadeNotas20, final Integer pQuantidadeNotas50) {
	notas10 = pQuantidadeNotas10;
	notas2 = pQuantidadeNotas2;
	notas20 = pQuantidadeNotas20;
	notas5 = pQuantidadeNotas5;
	notas50 = pQuantidadeNotas50;
    }

    public Integer getNotas10() {
	return notas10;
    }

    public Integer getNotas2() {
	return notas2;
    }

    public Integer getNotas20() {
	return notas20;
    }

    public Integer getNotas5() {
	return notas5;
    }

    public Integer getNotas50() {
	return notas50;
    }

    public void setNotas2(final Integer pNotas2) {notas2 = pNotas2;}
    public void setNotas5(final Integer pNotas5) {notas5 = pNotas5;}
    public void setNotas10(final Integer pNotas10) {notas10 = pNotas10;}
    public void setNotas20(final Integer pNotas20) {notas20 = pNotas20;}
    public void setNotas50(final Integer pNotas50) {notas50 = pNotas50;}

    public BigDecimal getValorTotal() {
	return new BigDecimal(notas50 * 50 + notas20 * 20 + notas10 * 10 + notas5 * 5 + notas2 * 2);
    }

    @Override public String toString() {
	return notas50 + ";" + notas20 + ";" + notas10 + ";" + notas5 + ";" + notas2;
    }
}
