package br.dev.hygino;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Servidor implements Runnable {

    private final Socket socket;

    public Servidor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            final double peso = entrada.readDouble();
            final double altura = entrada.readDouble();
            final double imc = peso / Math.pow(altura, 2);
            System.out.printf("Peso = %.4f\n", peso);
            System.out.printf("Altura = %.4f\n", altura);
            System.out.printf("IMC = %.4f\n", imc);
            entrada.close();
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Erro: " + ioe.toString());
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try (ServerSocket servidor = new ServerSocket(54321)) {
            while (true) {
                Socket conexao = servidor.accept();
                System.out.println("Um cliente se conectou...");
                Thread threadServidor = new Thread(new Servidor(conexao));
                threadServidor.start();
                threadServidor.join();
            }
        } catch (IOException | InterruptedException ioe) {
            System.out.println("Erro: " + ioe.getMessage());
        }
    }
}
