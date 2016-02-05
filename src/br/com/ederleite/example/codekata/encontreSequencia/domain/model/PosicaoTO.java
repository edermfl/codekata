package br.com.ederleite.example.codekata.encontreSequencia.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eml on 29/01/16.
 */
public class PosicaoTO {
    public List<Integer> listaPosicoesDireta = new ArrayList<Integer>();
    public List<Integer> listaPosicoesReversa = new ArrayList<Integer>();

    public void addPosicaoDireta(Integer pPosicao){
	listaPosicoesDireta.add(pPosicao);
    }

    public void addPosicaoReversa(Integer pPosicao){
	listaPosicoesReversa.add(pPosicao);
    }

    public List<Integer> getListaPosicoesDireta() {
	return listaPosicoesDireta;
    }

    public List<Integer> getListaPosicoesReversa() {
	return listaPosicoesReversa;
    }
}
