package app;

import app.model.Emprestimo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class ServidorUDP {

    private static final int porta = 53000;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(porta)) {
            while (true) {
                System.out.println("Aguardando conexão...");
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket pacoteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(pacoteEntrada);

                var endereco = pacoteEntrada.getAddress();
                int porta = pacoteEntrada.getPort();

                System.out.printf("Objeto recebido de: %s %d", endereco, porta);

                //deserializa o objeto
                ByteArrayInputStream bais = new ByteArrayInputStream(pacoteEntrada.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Emprestimo emprestimo = (Emprestimo) ois.readObject();
                ois.close();

                // Exibe a mensagem do objeto
                System.out.println("Mensagem recebida: " + emprestimo);

                final double parcela = calcularParcela(emprestimo);

                byte[] bufferSaida = new byte[8]; // Tamanho de um double em bytes

                // Converte o double para bytes
                ByteBuffer byteBuffer = ByteBuffer.wrap(bufferSaida);
                byteBuffer.putDouble(parcela);

                // Envia o pacote
                DatagramPacket packet = new DatagramPacket(bufferSaida, bufferSaida.length, endereco, porta);
                socket.send(packet);
                System.out.println("Valor enviado: " + parcela);
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static double calcularParcela(Emprestimo emprestimo) {
        final double i = emprestimo.taxa() / 100.0;
        return (emprestimo.valor() * i * Math.pow(1 + i, emprestimo.prestacoes())) / (Math.pow(1 + i, emprestimo.prestacoes()) - 1);
    }
}
