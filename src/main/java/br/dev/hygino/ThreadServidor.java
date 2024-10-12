package br.dev.hygino;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class ThreadServidor extends Thread {

    private final Socket conexao;

    public ThreadServidor(Socket conexao) {
        this.conexao = conexao;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(conexao.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());

            final double valor = entrada.readDouble();
            final double taxa = entrada.readDouble();
            final int periodo = entrada.readInt();

            StringBuilder sb = new StringBuilder("Dados recebidos do Cliente\n");
            sb.append(String.format("Valor: %.2f\n", valor));
            sb.append(String.format("Taxa: %.2f\n", taxa));
            sb.append(String.format("Periodo: %d\n", periodo));
            System.out.println(sb.toString());

            System.out.println("Enviado lista de parcelas ao Cliente");

            saida.writeObject(calcularParcelas(valor, taxa, periodo));
            conexao.close();
        } catch (IOException e) {
            System.out.println("Houve erro no servidor");
        }
    }

    private List<Parcela> calcularParcelas(double valor, double taxa, int periodo) {
        List<Parcela> parcelas = new ArrayList<>();
        final double i = taxa / 100.0;
        final double pmt = (valor * i * Math.pow(1 + i, periodo)) / (Math.pow(1 + i, periodo) - 1);
        double saldo = valor;

        for (int p = 1; p <= periodo; p++) {
            final double juros = saldo * i;
            final double amortizacao = pmt - juros;
            saldo -= amortizacao;

            parcelas.add(new Parcela(p, pmt, juros, amortizacao, saldo));
        }
        return parcelas;
    }
}
