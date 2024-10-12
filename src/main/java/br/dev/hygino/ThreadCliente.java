package br.dev.hygino;

import java.io.*;
import java.net.Socket;
import java.util.List;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;

public final class ThreadCliente extends Thread {

    private final double valor;
    private final double taxa;
    private final int periodo;
    private JTable tabela;
    private JLabel lbTotalJuros;

    private DataOutputStream saida;
    private ObjectInputStream entrada;

    public ThreadCliente(JTable tabela, JLabel lbTotalJuros, double valor, double taxa, int periodo) {
        this.tabela = tabela;
        this.lbTotalJuros = lbTotalJuros;
        this.valor = valor;
        this.taxa = taxa;
        this.periodo = periodo;
    }

    @Override
    public void run() {
        try {
            System.out.println("Enviando dados ao Servidor");
            Socket conexao = new Socket("127.0.0.1", 57000);
            saida = new DataOutputStream(conexao.getOutputStream());
            entrada = new ObjectInputStream(conexao.getInputStream());

            saida.writeDouble(valor);
            saida.writeDouble(taxa);
            saida.writeInt(periodo);

            List<Parcela> parcelas = (List<Parcela>) entrada.readObject();

            Object[][] dados = new Object[parcelas.size()][5];
            double totalJuros = 0.0;
            for (int i = 0; i < parcelas.size(); i++) {
                Parcela p = parcelas.get(i);
                dados[i][0] = p.numero();
                dados[i][1] = String.format("%.2f", p.pmt());
                dados[i][2] = String.format("%.2f", p.juros());
                dados[i][3] = String.format("%.2f", p.amortizacao());
                dados[i][4] = String.format("%.2f", p.saldo());
                totalJuros += p.juros();
            }

            lbTotalJuros.setText(String.format("Total Juros %.2f", totalJuros));

            String[] colunas = {"Número", "Parcela", "Juros", "Amortização", "Saldo"};

            DefaultTableModel model = new DefaultTableModel(dados, colunas);
            tabela.setModel(model);

            conexao.close();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
