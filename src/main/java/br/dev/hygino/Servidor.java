package br.dev.hygino;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {

    private final Socket socket;

    public Servidor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            String mensagem = entrada.readUTF();
            mensagem = mensagem.toUpperCase();
            System.out.println("A mensagem em maicusculo: " + mensagem);
            entrada.close();
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Erro: " + ioe.toString());
        }
    }

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(54321)){
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
