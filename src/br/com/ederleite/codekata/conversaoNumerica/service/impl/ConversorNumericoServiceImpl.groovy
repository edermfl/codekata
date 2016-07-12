package br.com.ederleite.codekata.conversaoNumerica.service.impl;

import br.com.ederleite.codekata.conversaoNumerica.service.IConversorNumericoService;

/**
 * Created by eml on 24/06/16.
 */
public class ConversorNumericoServiceImpl implements IConversorNumericoService {

    enum NumeralRomano {
        M(1000),
        CM(900),
        D(500),
        CD(400),
        C(100),
        XC(90),
        L(50),
        XL(40),
        X(10),
        IX(9),
        V(5),
        IV(4),
        I(1),

        private final int valor;

        NumeralRomano(final int pNumeroIndoArabico) {
            valor = pNumeroIndoArabico
        }
    }

    @Override
    public String converterParaIndoArabico(final String pNumeroRomano) throws IllegalArgumentException {
        validarNumeralRomano(pNumeroRomano)
        return null;
    }

    void validarNumeralRomano(final String pNumeroRomano) {
        if(!pNumeroRomano.matches("[MDCLXVI]+")){
            throw new IllegalAccessException("N�mero '$pNumero' n�o � romano.")
        }
    }

    @Override
    public String converterParaRomano(final String pIndoArabico) throws IllegalArgumentException {
        Integer valorIndoArabico = obterValorInteiro(pIndoArabico)
        StringBuilder numeroRomano = new StringBuilder();
        construirNumeralRomano(valorIndoArabico, numeroRomano)
        return numeroRomano.toString();
    }

    private void construirNumeralRomano(int valorIndoArabico, StringBuilder numeroRomano, boolean multiploDeMil = false) {
        def iteratorNumeralRomano = NumeralRomano.values().iterator()
        while (valorIndoArabico > 0 || iteratorNumeralRomano.hasNext()) {
            //se passar de 4000, deve usar o multiplicador
            if (valorIndoArabico >= 4000) {
                int valorMultiploDeMil = valorIndoArabico / 1000
                construirNumeralRomano(valorMultiploDeMil, numeroRomano, true)
                valorIndoArabico = valorIndoArabico - (valorMultiploDeMil * 1000)
            }
            def numeralAtual = iteratorNumeralRomano.next()
            int quantNumeral = valorIndoArabico / numeralAtual.valor
            if (quantNumeral == 0) {
                continue
            }
            // um numeral n�o pode repetir mais de 3 vezes
            for (int i = 0; i < quantNumeral; i++) {
                numeroRomano.append(multiploDeMil ? "(${numeralAtual.name()})" : numeralAtual.name())
            }
            valorIndoArabico -= numeralAtual.valor * quantNumeral
        }
    }

    Integer obterValorInteiro(final String pNumero) {
        try {
            def valor = Integer.valueOf(pNumero)
            if (valor <= 0 || valor > 15000) {
                throw new IllegalAccessException("N�mero '$pNumero' maior que 15000.")
            }
            return valor
        } catch (NumberFormatException e) {
            throw new IllegalAccessException("N�mero '$pNumero' n�o � Indo Ar�bico.")
        }
    }
}
