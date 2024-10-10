package br.dev.hygino;

import java.io.Serializable;

public record Parcela(int numero, double pmt, double juros, double saldo) implements Serializable {

}
