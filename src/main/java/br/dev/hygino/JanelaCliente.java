package br.dev.hygino;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public final class JanelaCliente extends JFrame {

    private JPanel painel;
    private JLabel lbValor;
    private JLabel lbTaxa;
    private JLabel lbPeriodo;

    private JTextField txtValor;
    private JTextField txtTaxa;
    private JTextField txtPeriodo;
    private JTextArea txtResultado;

    private JButton btnCalcular;

    private void windowSetup() {
        setLayout(new FlowLayout());
        painel = new JPanel(new GridLayout(8, 1));
        lbValor = new JLabel("Valor");
        lbTaxa = new JLabel("Taxa");
        lbPeriodo = new JLabel("Periodo");
        txtValor = new JTextField();
        txtTaxa = new JTextField();
        txtPeriodo = new JTextField();
        txtResultado = new JTextArea();
        btnCalcular = new JButton("Calcular");

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    final double valor = Double.parseDouble(txtValor.getText());
                    final double taxa = Double.parseDouble(txtTaxa.getText());
                    final int periodo = Integer.parseInt(txtPeriodo.getText());
                    SwingUtilities.invokeLater(() -> new ThreadCliente(txtResultado, valor, taxa, periodo).start());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Formato numerico invalido");
                }
            }
        });

        painel.add(lbValor);
        painel.add(txtValor);
        painel.add(lbTaxa);
        painel.add(txtTaxa);
        painel.add(lbPeriodo);
        painel.add(txtPeriodo);
        painel.add(btnCalcular);

        final Font font = new Font("Arial", Font.PLAIN, 20);
        txtResultado.setFont(font);
        txtResultado.setPreferredSize(new Dimension(500, 200));

        txtValor.setText("12000");
        txtTaxa.setText("3");
        txtPeriodo.setText("6");

        add(painel);
        add(new JScrollPane(txtResultado));

        setTitle("Cliente Sistema de Amortização");
        setSize(768, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // Mover para o final
    }

    public JanelaCliente() {
        windowSetup();
    }

    public static void main(String[] args) {
        new JanelaCliente();
    }
}
