package br.com.ederleite.codekata.conversaoNumerica.service.impl;

import br.com.ederleite.codekata.conversaoNumerica.service.IConversorNumericoService;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ceb on 02/07/16.
 */
public class ConversorNumericoServiceBatistaoImpl implements IConversorNumericoService {

    private static LinkedHashMap<String, Integer> numeralRomano = Maps.newLinkedHashMap();

    public ConversorNumericoServiceBatistaoImpl() {
        numeralRomano.put("M", 1000);
        numeralRomano.put("CM", 900);
        numeralRomano.put("D", 500);
        numeralRomano.put("CD", 400);
        numeralRomano.put("C", 100);
        numeralRomano.put("XC", 90);
        numeralRomano.put("L", 50);
        numeralRomano.put("XL", 40);
        numeralRomano.put("X", 10);
        numeralRomano.put("IX", 9);
        numeralRomano.put("V", 5);
        numeralRomano.put("IV", 4);
        numeralRomano.put("I", 1);
    }

    @Override
    public String converterParaIndoArabico(String pNumeroRomano) throws IllegalArgumentException {
        if(Strings.isNullOrEmpty(pNumeroRomano)) {
            throw new IllegalArgumentException("Parametro com o valor do número romano é obrigatório!");
        }

        int totalDeParentesesAbrindo = CharMatcher.is('(').countIn(pNumeroRomano);
        int totalDeParentesesFechando = CharMatcher.is(')').countIn(pNumeroRomano);
        if(totalDeParentesesAbrindo != totalDeParentesesFechando) {
            throw new IllegalArgumentException("Apenas números romanos são aceitos");
        }

        String numeroRomanoUpper = pNumeroRomano.toUpperCase();

        Pattern p = Pattern.compile("\\(([\\w]*)\\)");
        Matcher m = p.matcher(numeroRomanoUpper);

        String numeroRomanoParaAnalisar = numeroRomanoUpper;
        String numeroRomanoMilhar = null;
        if(m.find()) {
            numeroRomanoMilhar = m.group(1);
            numeroRomanoParaAnalisar = numeroRomanoUpper.replaceAll("\\(([\\w]*)\\)", "");

            if(!numeroRomanoMilhar.replaceAll("\\(", "").replaceAll("\\)", "").matches("^(?=[MDCLXVI])M*(C[MD]|D?C*)(X[CL]|L?X*)(I[XV]|V?I*)$")) {
                throw new IllegalArgumentException("Apenas números romanos são aceitos");
            }
        }

        if(!Strings.isNullOrEmpty(numeroRomanoParaAnalisar) && !numeroRomanoParaAnalisar.replaceAll("\\(", "").replaceAll("\\)", "").matches("^(?=[MDCLXVI])M*(C[MD]|D?C*)(X[CL]|L?X*)(I[XV]|V?I*)$")) {
            throw new IllegalArgumentException("Apenas números romanos são aceitos");
        }

        Integer numeroIndoArabico = 0;
        if(!Strings.isNullOrEmpty(numeroRomanoMilhar)) {
            numeroIndoArabico += getNumeroDecimal(numeroRomanoMilhar) * 1000;
        }

        if(!Strings.isNullOrEmpty(numeroRomanoParaAnalisar)) {
            numeroIndoArabico += getNumeroDecimal(numeroRomanoParaAnalisar);
        }

        if(numeroIndoArabico > 15000) {
            throw new IllegalArgumentException("Range de números permitidos são de I ao (XV)");
        }

        return Integer.toString(numeroIndoArabico);
    }

    private static Integer getNumeroDecimal(String numeroRomanoParaAnalisar) {
        int numeroIndoArabico = 0;
        int tamanhoNumeroRomano = numeroRomanoParaAnalisar.length() - 1;
        for (int i = 0; i < tamanhoNumeroRomano; i++) {
            Integer numeroAtual = numeralRomano.get(Character.toString(numeroRomanoParaAnalisar.charAt(i)));
            Integer proximoNumero = numeralRomano.get(Character.toString(numeroRomanoParaAnalisar.charAt(i + 1)));

            if (numeroAtual < proximoNumero) {
                numeroIndoArabico -= numeroAtual;
            } else {
                numeroIndoArabico += numeroAtual;
            }
        }

        numeroIndoArabico += numeralRomano.get(Character.toString(numeroRomanoParaAnalisar.charAt(tamanhoNumeroRomano)));

        return numeroIndoArabico;
    }

    @Override
    public String converterParaRomano(String pIndoArabico) throws IllegalArgumentException {
        if(Strings.isNullOrEmpty(pIndoArabico)) {
            throw new IllegalArgumentException("Parametro com o valor do número indo-arábicos é obrigatório!");
        }

        Integer numeroIndoArabico = Ints.tryParse(pIndoArabico);
        if(numeroIndoArabico == null || numeroIndoArabico < 1 || numeroIndoArabico > 15000) {
            throw new IllegalArgumentException("Apenas números indo-arábicos de 1 à 15000 são aceitos");
        }

        String numeralRomano = getNumeralRomano(numeroIndoArabico);

        int totalDeM = CharMatcher.is('M').countIn(numeralRomano);
        if(totalDeM >= 4) {
            String thousandString = Strings.repeat("M", totalDeM);
            String novoNumeralRomano = "("+getNumeralRomano(totalDeM)+")";

            return numeralRomano.replace(thousandString, novoNumeralRomano);
        }

        return numeralRomano;
    }

    private static String getNumeralRomano(Integer numeroIndoArabico) {
        String numeroRomano = "";
        for(Map.Entry<String, Integer> entry : numeralRomano.entrySet()){
            int matches = numeroIndoArabico/entry.getValue();
            if(matches > 0) {
                numeroRomano += repeat(entry.getKey(), matches);
                numeroIndoArabico = numeroIndoArabico % entry.getValue();
            }
        }
        return numeroRomano;
    }

    private static String repeat(String numeroRomanoEncontrado, int totalLetrasRomanasEncontradas) {
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < totalLetrasRomanasEncontradas; i++) {
            sb.append(numeroRomanoEncontrado);
        }
        return sb.toString();
    }
}