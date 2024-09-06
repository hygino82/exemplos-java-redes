package br.dev.hygino.model;

import java.io.Serializable;

public record Emprestimo(double valor, double taxa, int parcelas) implements Serializable {

}
