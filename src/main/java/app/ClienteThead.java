package app;

import app.model.Emprestimo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ClienteThead extends Thread {

    private final Emprestimo emprestimo;
    private final int porta;
    private final JLabel lbParcela;

    public ClienteThead(Emprestimo emprestimo, int porta, JLabel lbParcela) {
        this.emprestimo = emprestimo;
        this.porta = porta;
        this.lbParcela = lbParcela;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {

            // Serializa o objeto
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(emprestimo);
            oos.flush();
            byte[] dadosSaida = baos.toByteArray();

            // Configura o socket UDP
            InetAddress endereco = InetAddress.getByName("127.0.0.1");

            // Cria o pacote de dados
            DatagramPacket pacoteSaida = new DatagramPacket(dadosSaida, dadosSaida.length, endereco, porta);

            // Envia o pacote ao Servidor
            socket.send(pacoteSaida);
            System.out.println("Objeto enviado!");

            //recebe dados do Servidor 
            byte[] bufferEntrada = new byte[8]; // tamanho de um double em bytes
            DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            socket.receive(packetEntrada);
            ByteBuffer byteBuffer = ByteBuffer.wrap(bufferEntrada);
            double parcela = byteBuffer.getDouble();

            lbParcela.setText(String.format("%.2f", parcela));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
