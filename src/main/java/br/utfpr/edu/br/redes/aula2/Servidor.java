package br.utfpr.edu.br.redes.aula2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {

    private Socket conexao;

    public Servidor() {
    }

    public Servidor(Socket conexao) {
        this.conexao = conexao;
    }

    @Override
    public void run() {
        try (DataInputStream entrada = new DataInputStream(conexao.getInputStream()); 
                DataOutputStream saida = new DataOutputStream(conexao.getOutputStream())) {

            String frase = entrada.readUTF();

            String novaFrase = frase.toUpperCase();
            saida.writeUTF(novaFrase);
            conexao.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(52000);
            while (true) {
                System.out.println("Aguardando conexao...");
                Socket conexao = servidor.accept();
                Servidor tServidor = new Servidor(conexao);
                tServidor.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
