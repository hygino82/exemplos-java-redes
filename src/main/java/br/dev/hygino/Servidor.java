package br.dev.hygino;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import br.dev.hygino.model.Parcela;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(54321)) {
            Socket conexao = servidor.accept();
            System.out.println("Cliente conectado...");
            DataInputStream entrada = new DataInputStream(conexao.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
            System.out.println("Dados recebidos do Cliente");
            // dados recebidos
            final double valor = entrada.readDouble();
            final double taxa = entrada.readDouble();
            final int periodo = entrada.readInt();
            System.out.printf("Valor: %.2f\n", valor);
            System.out.printf("taxa: %.2f\n", taxa);
            System.out.printf("Valor: %d\n", periodo);
            // cálculo das parcelas
            final List<Parcela> parcelas = calcularParcelas(valor, taxa, periodo);
            // dados enviados ao cliente
            saida.writeObject(parcelas);
            System.out.println("Dados enviados ao Cliente");
            // fechando a entrada e a saída
            entrada.close();
            saida.close();
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private static List<Parcela> calcularParcelas(double valor, double taxa, int periodo) {
        List<Parcela> listaParcelas = new ArrayList<>();
        final double pmt = (valor * taxa * Math.pow(1 + taxa, periodo)) / (Math.pow(1 + taxa, periodo) - 1);
        // lambda não aceita mudar valor do saldo
        double saldo = valor;
        for (int i = 1; i <= periodo; i++) {
            final var juros = saldo * taxa;
            final var amortizacao = pmt - juros;
            saldo -= amortizacao;
            listaParcelas.add(new Parcela(i, pmt, juros, amortizacao, saldo));
        }
        return listaParcelas;
    }
}