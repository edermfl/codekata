package br.com.ederleite.codekata.conversaoNumerica.service.impl;

import br.com.ederleite.codekata.conversaoNumerica.service.IConversorNumericoService;

/**
 * Created by eml on 24/06/16.
 */
public class ConversorNumericoServiceImpl implements IConversorNumericoService {

    enum NumeralRomano {
        M(1000),
        D(500),
        C(100),
        L(50),
        X(10),
        V(5),
        I(1),

        private final int valor;

        NumeralRomano(final int pNumeroIndoArabico) {
            valor = pNumeroIndoArabico
        }

        public obterNumeral(int pValor) {
            values()*.valor.find { it == pValor }
        }
    }

    @Override
    public String converterParaIndoArabico(final String pNumeroRomano) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String converterParaRomano(final String pIndoArabico) throws IllegalArgumentException {
        Integer valorIndoArabico = obterValorInteiro(pIndoArabico)
        StringBuilder numeroRomano = new StringBuilder();
        def iteratorNumeralRomano = NumeralRomano.values().iterator()
        while (valorIndoArabico > 0 || iteratorNumeralRomano.hasNext()) {
            def numeralAtual = iteratorNumeralRomano.next()
            BigDecimal resultadoDivisao = valorIndoArabico / numeralAtual.valor
            int quantNumeral = resultadoDivisao.intValue()

            if (quantNumeral == 0) {
                continue;
            }
            // um numeral não pode repetir mais de 3 vezes
            if (quantNumeral > 3) {
                if (resultadoDivisao.precision() != 0) {
                    numeroRomano.append(numeralAtual.name())
                    numeroRomano.append(numeralAtual.previous().name())
                }
            } else {
                for (int i = 0; i < quantNumeral; i++) {
                    numeroRomano.append(numeralAtual.name())
                }
            }
            valorIndoArabico -= numeralAtual.valor * quantNumeral
        }
        return numeroRomano.toString();
    }

    Integer obterValorInteiro(final String pNumero) {
        try {
            def valor = Integer.valueOf(pNumero)
            if (valor > 15000) {
                throw new IllegalAccessException("Número '$pNumero' maior que 15000.")
            }
            return valor
        } catch (NumberFormatException e) {
            throw new IllegalAccessException("Número '$pNumero' não é Indo Arábico.")
        }
    }
}
