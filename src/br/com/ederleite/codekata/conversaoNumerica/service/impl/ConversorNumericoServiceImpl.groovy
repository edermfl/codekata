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
        def numeraisSubtracao = [NumeralRomano.I, NumeralRomano.X, NumeralRomano.C]
        int valorIndoArabico = 0

        def tamanhoNumeral = pNumeroRomano.length()
        for (int i = 0; i < tamanhoNumeral; i++) {

            int multiplicador = 1
            String caracter = pNumeroRomano[i]
            if (caracter == '(') {
                caracter = ''
                while(1) {
                    def proximo = pNumeroRomano[++i]
                    if(proximo == ')') break;
                    caracter += proximo
                }
                multiplicador = 1000
            }

            def numeral = NumeralRomano.valueOf(caracter)

            if (numeraisSubtracao.contains(numeral) && i+1 < tamanhoNumeral) {
                def proximoCaracter = pNumeroRomano[i + 1]
                try {
                    def numeralComposto = NumeralRomano.valueOf(caracter + proximoCaracter)
                    numeral = numeralComposto
                    i++
                } catch (e) {
                    // continue
                }
            }
            valorIndoArabico += numeral.valor * multiplicador
        }
        obterValorInteiro(valorIndoArabico.toString())
        return valorIndoArabico.toString();
    }

    void validarNumeralRomano(final String pNumeroRomano) {
        if (!pNumeroRomano.matches("[\\(\\)MDCLXVI]+")) {
            throw new IllegalAccessException("Número '$pNumeroRomano' não é romano.")
        }
        if (!pNumeroRomano.matches("(CM|M{0,3}|M.*|               D{0,3}|C{0,3}|L{0,3}|X{0,3}|V{0,3}|I{0,3})")) {
            throw new IllegalAccessException("Número '$pNumeroRomano' não é romano.")
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
            // um numeral não pode repetir mais de 3 vezes
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
                throw new IllegalAccessException("Número '$pNumero' maior que 15000.")
            }
            return valor
        } catch (NumberFormatException e) {
            throw new IllegalAccessException("Número '$pNumero' não é Indo Arábico.")
        }
    }
}
