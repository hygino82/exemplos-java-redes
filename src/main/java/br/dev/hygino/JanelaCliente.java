package br.dev.hygino;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public final class JanelaCliente extends JFrame {

    private JPanel painel;
    private JLabel lbValor;
    private JLabel lbTaxa;
    private JLabel lbPeriodo;

    private JTextField txtValor;
    private JTextField txtTaxa;
    private JTextField txtPeriodo;
    private JScrollPane painelTabela;
    private JTable tabela;
    private JLabel lbTotalJuros;

    private JButton btnCalcular;

    private void windowSetup() {
        setLayout(new FlowLayout());
        painel = new JPanel(new GridLayout(9, 1));
        lbValor = new JLabel("Valor");
        lbTaxa = new JLabel("Taxa");
        lbPeriodo = new JLabel("Periodo");
        txtValor = new JTextField();
        txtTaxa = new JTextField();
        txtPeriodo = new JTextField();

        lbTotalJuros = new JLabel("Total Juros");

        txtValor.setText("12000");
        txtTaxa.setText("3");
        txtPeriodo.setText("10");

        String[] colunas = {"Número", "Parcela", "Juros", "Amortização", "Saldo"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tabela = new JTable(model);

        painelTabela = new JScrollPane(tabela);

        btnCalcular = new JButton("Calcular");

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    final double valor = Double.parseDouble(txtValor.getText());
                    final double taxa = Double.parseDouble(txtTaxa.getText());
                    final int periodo = Integer.parseInt(txtPeriodo.getText());

                    SwingUtilities.invokeLater(() -> new ThreadCliente(tabela, lbTotalJuros, valor, taxa, periodo).start());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Formato numérico inválido");
                }
            }
        });

        // Adicionando os componentes ao painel
        painel.add(lbValor);
        painel.add(txtValor);
        painel.add(lbTaxa);
        painel.add(txtTaxa);
        painel.add(lbPeriodo);
        painel.add(txtPeriodo);
        painel.add(btnCalcular);

        painel.add(lbTotalJuros);

        add(painel);
        add(painelTabela);

        // Configuração da janela
        setTitle("Cliente Sistema de Amortização");
        setSize(768, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // Mover para o final para garantir que tudo seja configurado
    }

    public JanelaCliente() {
        windowSetup();
    }

    public static void main(String[] args) {
        new JanelaCliente();
    }
}
