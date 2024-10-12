package br.dev.hygino;

import java.io.Serializable;

public record Parcela(int numero, double pmt, double juros, double amortizacao, double saldo) implements Serializable {

    @Override
    public String toString() {
        return String.format("%d\t%.2f\t%.2f\t%.2f\t%.2f", numero, pmt, juros, amortizacao, saldo);
    }
}
