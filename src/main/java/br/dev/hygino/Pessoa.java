package br.dev.hygino;

import java.io.Serializable;

public record Pessoa(String nome, String cpf, String email, int idade) implements Serializable {
}
