package app.model;

import java.io.Serializable;

public record Emprestimo(double valor, double taxa, int prestacoes) implements Serializable{

}
