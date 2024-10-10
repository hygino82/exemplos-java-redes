package br.dev.hygino;

import java.awt.*;
import javax.swing.*;

public class JanelaCliente extends JFrame {

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
        txtResultado = new JTextArea(30,80);
        btnCalcular = new JButton("Calcular");

        painel.add(lbValor);
        painel.add(txtValor);
        painel.add(lbTaxa);
        painel.add(txtTaxa);
        painel.add(lbPeriodo);
        painel.add(txtPeriodo);
        painel.add(btnCalcular);

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
