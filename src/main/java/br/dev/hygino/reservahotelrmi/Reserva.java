package br.dev.hygino.reservahotelrmi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String nome;
    private final Quarto quarto;
    private final LocalDateTime checkin;
    private LocalDateTime checkout;

    public Reserva(String nome, Quarto quarto) {
        this.nome = nome;
        this.quarto = quarto;
        checkin = LocalDateTime.now();
        checkout = null;
    }

    public String getNome() {
        return nome;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public LocalDateTime getCheckin() {
        return checkin;
    }

    public LocalDateTime getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDateTime checkout) {
        this.checkout = checkout;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (checkout == null) {
            return nome + ", quarto=" + quarto + ", entrada=" + checkin.format(formatter);
        }
        return nome + ", quarto=" + quarto + ", entrada=" + checkin.format(formatter) + ", saida=" + checkout.format(formatter);
    }
}
