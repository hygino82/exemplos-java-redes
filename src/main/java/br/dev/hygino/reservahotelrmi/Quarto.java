package br.dev.hygino.reservahotelrmi;

import java.io.Serializable;

public final class Quarto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idQuarto;
    private int tipoQuarto;
    private String descricao;
    private double preco;
    private boolean ocupado = false;

    public Quarto(int tipoQuarto, int idQuarto) {
        this.idQuarto = idQuarto;

        if (tipoQuarto < 0 || tipoQuarto > 4) {
            throw new IllegalArgumentException("Tipo de quarto invalido");
        }

        this.tipoQuarto = tipoQuarto;

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

    public int getIdQuarto() {
        return idQuarto;
    }

    @Override
    public String toString() {
        return idQuarto + ", " + descricao + ", " + preco;
    }
}
