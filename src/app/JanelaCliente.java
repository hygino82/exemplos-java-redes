package app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class JanelaCliente extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel painel;
	private JLabel lbPorta;
	private JLabel lbResultado;
	private JTextField txtPorta;
	private JTextField txtMensagem;
	private JButton btnEnviar;
	private JTextArea txtResultado;
	private JScrollPane scrollPane;

	public JanelaCliente() {
		configurarJanela();
	}

	private void configurarJanela() {
		setLayout(new FlowLayout());
		painel = new JPanel(new GridLayout(5, 1));
		lbPorta = new JLabel("Porta");
		lbResultado = new JLabel("Resultado");
		txtPorta = new JTextField();
		txtMensagem = new JTextField();
		txtPorta.setText("59000");
		btnEnviar = new JButton("Enviar");
		txtResultado = new JTextArea();

		scrollPane = new JScrollPane(txtResultado);
		scrollPane.setPreferredSize(new Dimension(200, 100));

		painel.add(lbPorta);
		painel.add(txtPorta);
		painel.add(txtMensagem);
		painel.add(btnEnviar);
		painel.add(lbResultado);

		add(painel);

		
		add(scrollPane);

		btnEnviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final int porta = Integer.parseInt(txtPorta.getText());
					String mensagem = txtMensagem.getText();
					SwingUtilities
							.invokeLater(() -> new ClienteThread(porta, lbResultado, mensagem, txtResultado).start());
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Formato numérico inválido");
				}
			}
		});
		
		setTitle("Envio mensagem");
		setSize(320, 240);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new JanelaCliente();
	}
}
