package br.dev.hygino;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Servidor {

    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(57000);

            while (true) {
                System.out.println("Aguardando conexoes...");
                Socket conexao = servidor.accept();
                
                ThreadServidor threadServidor = new ThreadServidor(conexao);
                threadServidor.start();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
