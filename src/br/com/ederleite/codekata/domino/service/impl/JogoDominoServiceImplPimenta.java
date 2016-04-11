package br.com.ederleite.codekata.domino.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.com.ederleite.codekata.domino.domain.model.PecaDomino;
import br.com.ederleite.codekata.domino.domain.model.Tabuleiro;
import br.com.ederleite.codekata.domino.service.IJogoDominoService;

public class JogoDominoServiceImplPimenta implements IJogoDominoService {

	@Override
	public Tabuleiro jogar(List<PecaDomino> pPecas)
			throws IllegalArgumentException {

		solucao = null;
		sobra = null;
		estatisticas = null;
		qtdpecas = null;
		
		validar(pPecas);

		Tabuleiro retorno = resolverJogo(pPecas);

		return retorno;
	}


	LinkedList<PecaDomino> solucao;
	ArrayList<PecaDomino> sobra;
	int[] estatisticas = new int[7];
	int[][] qtdpecas = new int[7][7];

	public JogoDominoServiceImplPimenta(){
		solucao = null;
		sobra = null;
		estatisticas = null;
		qtdpecas = null;
	}

	/**
	 * Metodo principal que orquestra a resolucao do problema
	 * @param pPecas lista de pecas informadas
	 * @return Tabuleiro com resolucao e sobra
	 */
	private Tabuleiro resolverJogo(List<PecaDomino> pPecas) {

		solucao = new LinkedList<PecaDomino>();
		sobra = new ArrayList<PecaDomino>();
		estatisticas = new int[7];
		qtdpecas = new int[7][7];

		/**
		 * Inicializar estatisticas de quais numeros aparecem nas pedras e quantas vezes
		 * contar qtd de cada peca disponivel armazenado na ponta de menor valor numerico
		 */
		for (PecaDomino pecaDomino : pPecas) {
			estatisticas[pecaDomino.getPontaA().intValue()] ++;
			if (pecaDomino.getPontaA().intValue() != pecaDomino.getPontaB().intValue()) { estatisticas[pecaDomino.getPontaB().intValue()] ++; }

			if (pecaDomino.getPontaB().intValue() < pecaDomino.getPontaA().intValue()){
				qtdpecas[pecaDomino.getPontaB().intValue()][pecaDomino.getPontaA().intValue()]++;
			}else{
				qtdpecas[pecaDomino.getPontaA().intValue()][pecaDomino.getPontaB().intValue()]++;
			}
		}

		/**
		 * Resolve primeira jogada			
		 */
		//debugVetores();
		int numeroMaisFrequente = getNumeroMaisFrequente(estatisticas);

		if (existePeca(numeroMaisFrequente, numeroMaisFrequente))	{
			jogarPeca(numeroMaisFrequente,numeroMaisFrequente,false);
		}
		else{
			jogarPeca(numeroMaisFrequente,getNumeroMaisFrequenteComPeca(estatisticas.clone(),numeroMaisFrequente),false);
		}


		/**
		 * Resolver demais jogadas
		 */
		int trava=0;
		while (pPecas.size() != (solucao.size() + sobra.size())){
			if (estatisticas[solucao.getFirst().getPontaA().intValue()] > 0 
					&& estatisticas[solucao.getFirst().getPontaA().intValue()] >  estatisticas[solucao.getLast().getPontaB().intValue()]){
				jogarPedraCompativelCom(solucao.getFirst().getPontaA().intValue(),false);
			}
			else if (estatisticas[solucao.getLast().getPontaB().intValue()] > 0 ){
				jogarPedraCompativelCom(solucao.getLast().getPontaB().intValue(),true);
			}
			else {
				popularSobra();
			}
			
			// trava para evitar loop infinito se algo inesperado acontecer :|
			if (trava >= pPecas.size()*10) {
				throw new IllegalArgumentException("Solucao impossivel. Um erro inesperado ocorreu e o programa foi abortado apos " + trava + " de interacoes");
			}		
			trava++;	
		}

		Tabuleiro resultado = new Tabuleiro();
		resultado.setPecasEncaixadas(solucao);
		resultado.setPecasSobraram(sobra);
		return resultado;
	}


	/**
	 * Popular lista de sobra com pecas que nao podemos mais ser jogadas
	 */
	private void popularSobra() {
		for (int i = 0; i < qtdpecas.length; i++) {
			for (int j = 0; j < qtdpecas[i].length; j++) {
				if  (qtdpecas[i][j] > 0 ) {
					for (int k = 0; k < qtdpecas[i][j];k++){
						sobra.add(new PecaDomino(i,j));
						qtdpecas[i][j]--;
						estatisticas[i]--;
						if (i != j) {estatisticas[j]--;}
					}
				}
			}
		}
		//DEBUG
		//System.out.println("### ACABOU ###");
	}

	/**
	 * 
	 * @param pPonta
	 */
	private void jogarPedraCompativelCom(int pPonta, boolean pIsLadoB) {
		if (existePeca(pPonta, pPonta)){
			jogarPeca(pPonta, pPonta, pIsLadoB);
		}
		else{
			int maiorEstatistica = 0;
			int valorPeca = -1;
			for (int i = 0; i < 7; i++) {
				if ( existePeca(pPonta, i) && estatisticas[i] >= maiorEstatistica ){
					valorPeca = i;
					maiorEstatistica = estatisticas[i];
				}
			}
			if (valorPeca == -1) {
				throw new IllegalArgumentException("Erro no algoritmo de resolucao! Algo inesperado ocorreu.");
			}
			else{
				jogarPeca(pPonta, valorPeca, pIsLadoB);
			}
		}
	}

	/**
	 * debug para teste de mesa
	 */
	private void debugVetores() {
		//DEBUG
		for (int i = 0; i < estatisticas.length; i++) {
			if (estatisticas[i]> 0) {System.out.println( "numero: "+i + ", freq: " + estatisticas[i]);}
		}
		for (int i = 0; i < qtdpecas.length; i++) {
			for (int j = 0; j < qtdpecas[i].length; j++) {
				if  (qtdpecas[i][j] > 0 ) {System.out.println( qtdpecas[i][j] + " Pedra(s) " + i + " | " + j);}
			}
		}
		System.out.println("SOLUCAO: ");
		for (PecaDomino peca : solucao) {
			System.out.print(peca.toString());
		}
		System.out.println("#");
	}

	/**
	 * Adiciona Peca[A,B] na solucao, desconta das estatisticas e das disponiveis.
	 * @param pPecaA ponta A da peca a ser jogada
	 * @param pPecaB ponta B da peca a ser jogada
	 * @pIsLadoB indica se a jogada eh na ponta A ou na ponta B da solucao
	 */
	private void jogarPeca(int pPecaA, int pPecaB, boolean pIsLadoB) {
		//System.out.println("JOGAR1 " + pPecaA + "|" + pPecaB);
		if (pPecaB < pPecaA){
			int aux = pPecaB;
			pPecaB = pPecaA;
			pPecaA = aux;
		}
		//System.out.println("JOGAR2 " + pPecaA + "|" + pPecaB);

		if (solucao.isEmpty()){
			solucao.addFirst( new PecaDomino(pPecaA,pPecaB));
		}
		else if (pIsLadoB){
			// ADICIONA PECA NO LADO B DA SOLUCAO
			if ( solucao.getLast().getPontaB().intValue() == pPecaA){
				solucao.addLast( new PecaDomino(pPecaA,pPecaB));
			}
			else{
				solucao.addLast( new PecaDomino(pPecaB,pPecaA));
			}
		}
		else {
			// ADICIONA PECA NO LADO A DA SOLUCAO
			if ( solucao.getFirst().getPontaA().intValue() == pPecaB){
				solucao.addFirst( new PecaDomino(pPecaA,pPecaB));
			}
			else{
				solucao.addFirst( new PecaDomino(pPecaB,pPecaA));
			}
		}
		qtdpecas[pPecaA][pPecaB]--;
		estatisticas[pPecaA]--;
		if (pPecaA != pPecaB) {estatisticas[pPecaB]--;}
		//DEBUG
		//System.out.println("usada peca " +pPecaA + "|" + pPecaB );
		//debugVetores();

	}


	/**
	 * Busca numero que aparece mais vezes considerando todas as pecas.
	 * @param pEstatisticas
	 * @return
	 */
	private int getNumeroMaisFrequente(int[] pEstatisticas) {
		int retorno = 0;
		for (int i = 0; i < pEstatisticas.length; i++) {
			if (pEstatisticas[i] > pEstatisticas[retorno]) {retorno = i;}
			//tODO: if pEstatisticas[i] == pEstatisticas[retorno] --> avaliar melhor jogo qdo tem empate
		}
		//DEBUG
		//System.out.println( "Numero mais frequente:" + retorno);

		return retorno;
	}

	/**
	 * Busca numero que aparece mais vezes considerando todas as pecas, exceto numero excecao (mais frequente)
	 * Valida que existe peca
	 * @param pEstatisticas
	 * @return
	 */
	private int getNumeroMaisFrequenteComPeca(int[] pEstatisticas,int pExcecao) {
		int retorno = pExcecao;
		pEstatisticas[pExcecao] = 0;
		for (int i = 0; i < pEstatisticas.length; i++) {
			if (pEstatisticas[i] > pEstatisticas[retorno] && existePeca(i, retorno)) { retorno = i;}
		}
		//DEBUG
		//System.out.println("proximo mais frequente com peca: " + retorno);

		return retorno;
	}


	/**
	 * Verifica se uma peca esta disponivel
	 * @param pA
	 * @param pB
	 * @return
	 */
	private boolean existePeca(int pA, int pB) {
		return (qtdpecas[pA][pB] > 0 || qtdpecas[pB][pA] > 0);
	}

	/**
	 * Validar restricoes das pecas
	 * @param pPecas
	 */
	private void validar(List<PecaDomino> pPecas) {
		/**
		 * 1 ≤ N ≤ 100 (N = Tamanho da lista)
		 */
		if (pPecas == null || pPecas.isEmpty() || pPecas.size() < 1 || pPecas.size() > 100){
			throw new IllegalArgumentException("Quantidade de pedras inválida!");
		}

		/**
		 * Valor das pecas de 0 a 6
		 */
		for (PecaDomino pecaDomino : pPecas) {
			if (pecaDomino.getPontaA() == null || pecaDomino.getPontaB() == null ||  pecaDomino.getPontaA().intValue() < 0 || pecaDomino.getPontaB().intValue() < 0 || pecaDomino.getPontaA().intValue() > 6 || pecaDomino.getPontaB().intValue() > 6 ){
				throw new IllegalArgumentException("Pedra inválida:" + pecaDomino.toString());
			}
		}

	}

	public static void main(String[] args) {
		System.out.println();
		List<PecaDomino> listaPecas = new ArrayList<PecaDomino>();
		listaPecas.add(new PecaDomino(1,5));
		listaPecas.add(new PecaDomino(2,1));
		
		for (int i = 0; i < 49; i++) {
			listaPecas.add(new PecaDomino(1,0));
			listaPecas.add(new PecaDomino(5,5));
			
		}
		System.out.println(System.currentTimeMillis());
		Tabuleiro resultado = new JogoDominoServiceImplPimenta().jogar(listaPecas);
		System.out.println(System.currentTimeMillis());
		System.out.println( "solucao: " + resultado.getPecasEncaixadas().size() + "pecas, tabuleiro:" + resultado.getPecasEncaixadas().toString() );
		System.out.println( "sobraram: " + resultado.getPecasSobraram().size() + "pecas, lista:" + resultado.getPecasSobraram().toString());

		
		
	}

}
