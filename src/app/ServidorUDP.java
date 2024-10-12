package app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServidorUDP {

	public static void main(String[] args) {
		try {
			DatagramSocket serverSocket = new DatagramSocket(59000);

			while (true) {
				byte[] dadosEnviar = new byte[1024];
				byte[] dadosReceber = new byte[1024];
				System.out.println("Aguardando conexão...");
				
				DatagramPacket pacoteReceber = new DatagramPacket(dadosReceber, dadosReceber.length);
				serverSocket.receive(pacoteReceber);

				String mensagem = new String(pacoteReceber.getData());

				String novaMensagem = mensagem.toUpperCase();
				dadosEnviar = novaMensagem.getBytes();

				InetAddress endereco = pacoteReceber.getAddress();
				int porta = pacoteReceber.getPort();
				
				DatagramPacket pacoteEnviar = new DatagramPacket(dadosEnviar, dadosEnviar.length, endereco, porta);
				serverSocket.send(pacoteEnviar);
				System.out.println("Mensagem enviada ao Cliente!");
			}
		} catch (SocketException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
