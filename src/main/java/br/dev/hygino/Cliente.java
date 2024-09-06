package br.dev.hygino;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        try (Socket conexao = new Socket("127.0.0.1", 54321); DataOutputStream saida = new DataOutputStream(conexao.getOutputStream())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Digite uma mensagem: ");
            String mensagem = br.readLine();

            saida.writeUTF(mensagem);
        } catch (IOException ioe) {
            System.out.println("Erro: " + ioe.getMessage());
        }
    }
}
