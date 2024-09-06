package br.dev.hygino.model;

import java.io.Serializable;

public record Parcela(
        int numero,
        double pmt,
        double juros,
        double amortizacao,
        double saldo) implements Serializable {

    @Override
    public String toString() {
        return String.format("%d  %.2f  %.2f  %.2f %.2f", numero, pmt, juros, amortizacao, saldo);
    }
}
