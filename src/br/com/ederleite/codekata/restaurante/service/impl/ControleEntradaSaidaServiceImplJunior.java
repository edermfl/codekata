package br.com.ederleite.codekata.restaurante.service.impl;

import br.com.ederleite.codekata.restaurante.service.IControleEntradaSaidaService;

import java.util.List;

/**
 * Created by eml on 02/03/16.
 */
public class ControleEntradaSaidaServiceImplJunior implements IControleEntradaSaidaService {

    @Override
    public Integer calcularMaximoPessoasSimultaneamenteNoRestaurante(final List<Integer> pListaEntradas,
		    final List<Integer> pListaSaidas) {

	int primeiraEntrada = 0;
	int primeiraSaida = 0;

	int resultado = 1;
	int cont = 0;

	for (int a = 0, b = 0; a < pListaEntradas.size() && b < pListaSaidas.size(); a++, b++) {

	    if (cont < 1) {
		primeiraEntrada = pListaEntradas.get(a);
		primeiraSaida = pListaSaidas.get(b);
		cont++;
		continue;
	    }

	    if ((pListaEntradas.get(a) >= primeiraEntrada) && (pListaEntradas.get(a) <= primeiraSaida)) {
		resultado++;
	    }

	}
	return resultado;
    }
}
