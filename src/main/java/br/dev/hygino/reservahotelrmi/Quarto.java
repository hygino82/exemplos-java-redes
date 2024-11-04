package br.dev.hygino.reservahotelrmi;

import java.util.UUID;

public final class Quarto {

    private UUID idQuarto;
    private int tipoQuarto;
    private String descricao;
    private double preco;
    private boolean ocupado = false;

    public Quarto(int tipoQuarto) {
        if (tipoQuarto < 0 || tipoQuarto > 4) {
            throw new IllegalArgumentException("Tipo de quarto invalido");
        }

        this.tipoQuarto = tipoQuarto;
        idQuarto = UUID.randomUUID();

        switch (tipoQuarto) {
            case 0:
                descricao = "Quarto individual";
                preco = 100.0;
                break;
            case 1:
                descricao = "Quarto duplo";
                preco = 150.0;
                break;
            case 2:
                descricao = "Quarto duplo";
                preco = 200.0;
                break;
            case 3:
                descricao = "Quarto triplo";
                preco = 250.0;
                break;
            case 4:
                descricao = "Quarto quadruplo";
                preco = 300.0;
                break;
        }
    }

    public int getTipoQuarto() {
        return tipoQuarto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public UUID getIdQuarto() {
        return idQuarto;
    }

    @Override
    public String toString() {
        return "Quarto{" + "idQuarto=" + idQuarto + ", tipoQuarto=" + tipoQuarto + ", descricao=" + descricao + ", preco=" + preco + ", ocupado=" + ocupado + '}';
    }
}
