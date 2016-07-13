package br.com.ederleite.codekata.conversaoNumerica.service.impl;

import br.com.ederleite.codekata.conversaoNumerica.service.IConversorNumericoService;

/**
 * Created by mateus marquezini on 24-06-2016.
 */
public class ConversorNumericoServiceMateusImpl implements IConversorNumericoService {

    public static int calculaValoresDecimais(int pDecimalAtual, int pUltimoNumero, int pUltimoDecimal) {
	if (pUltimoNumero > pDecimalAtual) {
	    return pUltimoDecimal - pDecimalAtual;
	} else {
	    return pUltimoDecimal + pDecimalAtual;
	}
    }

    public String converterParaIndoArabico(String pNumeroRomano) throws IllegalArgumentException {
	int decimalAtualNoContexto = 0;
	int ultimoValorDecimal = 0;
	String numeroRomano = pNumeroRomano.toUpperCase();
	for (int i = numeroRomano.length() - 1; i >= 0; i--) {
	    char charNumRomano = numeroRomano.charAt(i);

	    switch (charNumRomano) {
		case 'M':
		    decimalAtualNoContexto = calculaValoresDecimais(1000, ultimoValorDecimal, decimalAtualNoContexto);
		    ultimoValorDecimal = 1000;
		    break;

		case 'D':
		    decimalAtualNoContexto = calculaValoresDecimais(500, ultimoValorDecimal, decimalAtualNoContexto);
		    ultimoValorDecimal = 500;
		    break;

		case 'C':
		    decimalAtualNoContexto = calculaValoresDecimais(100, ultimoValorDecimal, decimalAtualNoContexto);
		    ultimoValorDecimal = 100;
		    break;

		case 'L':
		    decimalAtualNoContexto = calculaValoresDecimais(50, ultimoValorDecimal, decimalAtualNoContexto);
		    ultimoValorDecimal = 50;
		    break;

		case 'X':
		    decimalAtualNoContexto = calculaValoresDecimais(10, ultimoValorDecimal, decimalAtualNoContexto);
		    ultimoValorDecimal = 10;
		    break;

		case 'V':
		    decimalAtualNoContexto = calculaValoresDecimais(5, ultimoValorDecimal, decimalAtualNoContexto);
		    ultimoValorDecimal = 5;
		    break;

		case 'I':
		    decimalAtualNoContexto = calculaValoresDecimais(1, ultimoValorDecimal, decimalAtualNoContexto);
		    ultimoValorDecimal = 1;
		    break;
	    }
	}

	return String.valueOf(decimalAtualNoContexto);
    }

    public String converterParaRomano(final String pIndoArabico) throws IllegalArgumentException {
	Integer numeroIndoArabico = Integer.valueOf(pIndoArabico);
	String numeroRomano = "";

	if (numeroIndoArabico <= 0) {
	    throw new IllegalArgumentException();
	}

	if (numeroIndoArabico <= 15000) {
	    while (numeroIndoArabico >= 15000) {
		numeroRomano += "(X)(V)";
		numeroIndoArabico -= 15000;
	    }
	    while (numeroIndoArabico >= 14000) {
		numeroRomano += "(X)(I)(V)";
		numeroIndoArabico -= 14000;
	    }
	    while (numeroIndoArabico >= 13000) {
		numeroRomano += "(X)MMM";
		numeroIndoArabico -= 13000;
	    }
	    while (numeroIndoArabico >= 12000) {
		numeroRomano += "(X)MM";
		numeroIndoArabico -= 12000;
	    }
	    while (numeroIndoArabico >= 11000) {
		numeroRomano += "(X)M";
		numeroIndoArabico -= 11000;
	    }
	    while (numeroIndoArabico >= 10000) {
		numeroRomano += "(X)";
		numeroIndoArabico -= 10000;
	    }
	    while (numeroIndoArabico >= 9000) {
		numeroRomano += "(I)(X)";
		numeroIndoArabico -= 9000;
	    }
	    while (numeroIndoArabico >= 8000) {
		numeroRomano += "(V)(I)(I)(I)";
		numeroIndoArabico -= 8000;
	    }
	    while (numeroIndoArabico >= 7000) {
		numeroRomano += "(V)(I)(I)";
		numeroIndoArabico -= 7000;
	    }
	    while (numeroIndoArabico >= 6000) {
		numeroRomano += "(V)(I)";
		numeroIndoArabico -= 6000;
	    }
	    while (numeroIndoArabico >= 5000) {
		numeroRomano += "(V)";
		numeroIndoArabico -= 5000;
	    }
	    while (numeroIndoArabico >= 4000) {
		numeroRomano += "(I)(V)";
		numeroIndoArabico -= 4000;
	    }
	    while (numeroIndoArabico >= 3000) {
		numeroRomano += "MMM";
		numeroIndoArabico -= 3000;
	    }
	    while (numeroIndoArabico >= 2000) {
		numeroRomano += "MM";
		numeroIndoArabico -= 2000;
	    }
	    while (numeroIndoArabico >= 1000) {
		numeroRomano += "M";
		numeroIndoArabico -= 1000;
	    }
	    while (numeroIndoArabico >= 900) {
		numeroRomano += "CM";
		numeroIndoArabico -= 900;
	    }
	    while (numeroIndoArabico >= 500) {
		numeroRomano += "D";
		numeroIndoArabico -= 500;
	    }
	    while (numeroIndoArabico >= 400) {
		numeroRomano += "CD";
		numeroIndoArabico -= 400;
	    }
	    while (numeroIndoArabico >= 100) {
		numeroRomano += "C";
		numeroIndoArabico -= 100;
	    }
	    while (numeroIndoArabico >= 90) {
		numeroRomano += "XC";
		numeroIndoArabico -= 90;
	    }
	    while (numeroIndoArabico >= 50) {
		numeroRomano += "L";
		numeroIndoArabico -= 50;
	    }
	    while (numeroIndoArabico >= 40) {
		numeroRomano += "XL";
		numeroIndoArabico -= 40;
	    }
	    while (numeroIndoArabico >= 10) {
		numeroRomano += "X";
		numeroIndoArabico -= 10;
	    }
	    while (numeroIndoArabico >= 9) {
		numeroRomano += "IX";
		numeroIndoArabico -= 9;
	    }
	    while (numeroIndoArabico >= 5) {
		numeroRomano += "V";
		numeroIndoArabico -= 5;
	    }
	    while (numeroIndoArabico >= 4) {
		numeroRomano += "IV";
		numeroIndoArabico -= 4;
	    }
	    while (numeroIndoArabico >= 1) {
		numeroRomano += "I";
		numeroIndoArabico -= 1;
	    }
	} else {
	    throw new IllegalArgumentException();
	}
	return numeroRomano;
    }
}