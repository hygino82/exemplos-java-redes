package br.dev.hygino.reservahotelrmi;

import java.io.Serializable;
import java.time.LocalDateTime;

public record Reserva(String nome, Quarto quarto, LocalDateTime checkin) implements Serializable {

    private static final long serialVersionUID = 1L;
}
