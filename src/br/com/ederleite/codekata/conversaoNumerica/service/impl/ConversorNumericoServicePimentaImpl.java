package br.com.ederleite.codekata.conversaoNumerica.service.impl;

import br.com.ederleite.codekata.conversaoNumerica.service.IConversorNumericoService;

import java.util.HashMap;


public class ConversorNumericoServicePimentaImpl implements IConversorNumericoService {

	
	HashMap<Integer, String> mapaDecimalRomano = new HashMap<Integer, String>();
	HashMap<String, Integer> valorLetra = new HashMap<String, Integer>();
	
	/**
	 * 
	 */
	@Override public String converterParaIndoArabico(final String pNumeroRomano) throws IllegalArgumentException {

		popularMapas();
		
		validarSeRomanoEhValido(pNumeroRomano);
		
		String numeroRomanoDesmembrado = sintetizarNumeroDecimal(pNumeroRomano);
		int numeroArabico = calcularNumeroArabico(numeroRomanoDesmembrado);
		if (numeroArabico > 15000 || numeroArabico < 1){
			throw new IllegalArgumentException("Valor informado: " + numeroArabico +" fora do range de 1 a 15000.");
		}
		return String.valueOf(numeroArabico);
	}

	private void validarSeRomanoEhValido(String pNumeroRomano) {
		
		if (pNumeroRomano.contains("IIII") 
				|| pNumeroRomano.contains("XXXX") 
				|| pNumeroRomano.contains("CCCC") 
				|| pNumeroRomano.contains("MMMM")
				|| pNumeroRomano.contains("DD")
				|| pNumeroRomano.contains("VV")
				|| pNumeroRomano.contains("LL")
				|| pNumeroRomano.contains("IIV")
				|| pNumeroRomano.contains("IIIV")
				|| pNumeroRomano.contains("IIX")
				|| pNumeroRomano.contains("IIIX")
				|| pNumeroRomano.contains("XXC")
				|| pNumeroRomano.contains("XXXC")
				|| pNumeroRomano.contains("CCM")
				|| pNumeroRomano.contains("CCCM")
				){
			throw new IllegalArgumentException("Valor informado: " + pNumeroRomano +" nao indica um numero Romano.");
		}
		
	}

	private int calcularNumeroArabico(String numeroRomanoDesmembrado) {
		int retorno = 0;
		String letra = "";
		for (int indiceLetra = 0; indiceLetra < numeroRomanoDesmembrado.length(); indiceLetra++) {
			letra = String.valueOf(numeroRomanoDesmembrado.charAt(indiceLetra));
			retorno += valorLetra.get(letra).intValue();
		}
		return retorno;
	}

		
	
	/**
	 * 
	 */
	@Override public String converterParaRomano(final String pIndoArabico) throws IllegalArgumentException {
		int numeroArabico = Integer.valueOf(pIndoArabico).intValue();
		popularMapas();

		return converterParaRomano(numeroArabico);
	}

	private void popularMapas(){
		
		mapaDecimalRomano.put(Integer.valueOf(1), "I");
		mapaDecimalRomano.put(Integer.valueOf(10), "X");
		mapaDecimalRomano.put(Integer.valueOf(100), "C");
		mapaDecimalRomano.put(Integer.valueOf(1000), "M");
		mapaDecimalRomano.put(Integer.valueOf(10000), "(X)");
		
		valorLetra.put("I",Integer.valueOf(1) );
		valorLetra.put("X", Integer.valueOf(10));
		valorLetra.put("C",Integer.valueOf(100));
		valorLetra.put("M",Integer.valueOf(1000));
		

	}

	private String converterParaRomano(int numeroArabico) {
		StringBuffer numeroRomano = new StringBuffer();
		int potencia = String.valueOf(numeroArabico).length()-1;

		if (numeroArabico > 15000 || numeroArabico < 1){
			throw new IllegalArgumentException("Valor informado: " + numeroArabico +" fora do range de 1 a 15000.");
		}
		else if (numeroArabico < 10) {
			numeroRomano = gerarSequencia(numeroArabico, mapaDecimalRomano.get(1) );
		}else{
			int quociente = (int) (numeroArabico / Math.pow(10,potencia));
			int resto = (int) (numeroArabico % Math.pow(10,potencia));
			numeroRomano = gerarSequencia(quociente, mapaDecimalRomano.get(Integer.valueOf( (int) Math.pow(10,potencia)  )) );
			if (resto > 0){	    			
				numeroRomano.append(converterParaRomano(resto));
			}
		}
		numeroRomano = sintetizarNumeroRomano(numeroRomano);
		return numeroRomano.toString();
	}

	private StringBuffer sintetizarNumeroRomano(StringBuffer pNumeroRomano) {
		String resultado = pNumeroRomano.toString().replace("IIIII", "V");
		resultado = resultado.replace("VIIII", "IX");
		resultado = resultado.replace("IIII", "IV");

		resultado = resultado.replace("XXXXX", "L");
		resultado = resultado.replace("LXXXX", "XC");
		resultado = resultado.replace("XXXX",  "XL");

		resultado = resultado.replace("CCCCC", "D");
		resultado = resultado.replace("DCCCC", "CM");
		resultado = resultado.replace("CCCC", "CD");

		resultado = resultado.replace("MMMMM", "(V)");
		resultado = resultado.replace("(V)MMMM", "(I)(X)");
		resultado = resultado.replace("MMMM", "(I)(V)");

		return new StringBuffer(resultado);
	}

	private String sintetizarNumeroDecimal(String pNumeroRomano) {
		String resultado = "";

		resultado = pNumeroRomano.replace("(I)(V)","MMMM" );
		resultado = resultado.replace("(I)(X)","(V)MMMM" );
		resultado = resultado.replace("(V)","MMMMM");
		resultado = resultado.replace("(X)","MMMMMMMMMM");

		resultado = resultado.replace("IV", "IIII");
		resultado = resultado.replace("IX", "VIIII");
		resultado = resultado.replace("V", "IIIII");

		resultado = resultado.replace("XL","XXXX");
		resultado = resultado.replace("XC","LXXXX");
		resultado = resultado.replace("L","XXXXX");

		resultado = resultado.replace("CD","CCCC");
		resultado = resultado.replace("CM","DCCCC");
		resultado = resultado.replace("D","CCCCC");

		return resultado;
	}

	private StringBuffer gerarSequencia(int pNumeroArabico, String pLetra) {
		StringBuffer resultado = new StringBuffer();
		for (int i = 0; i < pNumeroArabico; i++) {
			resultado.append(pLetra);
		}
		return resultado;
	}

	public static void main(String[] args) {
		ConversorNumericoServicePimentaImpl conversor = new ConversorNumericoServicePimentaImpl();

		for (int i = 1; i < 15002; i++) {
			String romano = conversor.converterParaRomano(String.valueOf(i));
			String arabico = conversor.converterParaIndoArabico(romano);
			if (Integer.valueOf(arabico).intValue() != i ){
				System.out.println("** ERRRO AQUI ***");
			}

			System.out.println(String.valueOf(i) + " : " + romano + " : " + arabico);

		}
	}

}