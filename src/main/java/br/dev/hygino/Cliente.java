package br.dev.hygino;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try (Socket conexao = new Socket("127.0.0.1", 54321); DataOutputStream saida = new DataOutputStream(conexao.getOutputStream())) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Informe o peso: ");
            final double peso = sc.nextDouble();
            System.out.print("Informe a altura: ");
            final double altura = sc.nextDouble();

            saida.writeDouble(peso);
            saida.writeDouble(altura);
        } catch (IOException ioe) {
            System.out.println("Erro: " + ioe.getMessage());
        }
    }
}
