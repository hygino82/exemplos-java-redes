package app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ClienteThread extends Thread {
	private final int porta;
	private InetAddress endereco;
	private JLabel lbResultado;
	private JTextArea txtResultado;
	private String mensagem;

	public ClienteThread(int porta, JLabel lbResultado, String mensagem, JTextArea txtResultado) {
		this.txtResultado = txtResultado;
		this.porta = porta;
		this.lbResultado = lbResultado;
		this.mensagem = mensagem;
	}

	@Override
	public void run() {
		try {
			DatagramSocket datagrama = new DatagramSocket();

			byte[] dadosEnviar;
			dadosEnviar = mensagem.getBytes();
			endereco = InetAddress.getByName("127.0.0.1");

			DatagramPacket pacoteEnviar = new DatagramPacket(dadosEnviar, dadosEnviar.length, endereco, porta);

			datagrama.send(pacoteEnviar);

			byte[] dadosReceber = new byte[1024];
			DatagramPacket pacoteReceber = new DatagramPacket(dadosReceber, dadosReceber.length);
			datagrama.receive(pacoteReceber);
			String novaMensagem = new String(pacoteReceber.getData());
			lbResultado.setText(novaMensagem);
			txtResultado.append(novaMensagem + '\n');

			datagrama.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

}
