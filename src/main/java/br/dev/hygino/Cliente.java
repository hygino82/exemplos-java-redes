package br.dev.hygino;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import br.dev.hygino.model.Parcela;

public class Cliente {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        try (Socket conexao = new Socket("127.0.0.1", 54321);
                DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
                Scanner sc = new Scanner(System.in);) {

            System.out.print("Informe o valor: ");
            final double valor = sc.nextDouble();
            System.out.print("Informe a taxa de juros: ");
            final double taxa = sc.nextDouble();

            System.out.print("Informe a quantidade de parcelas: ");
            final int periodo = sc.nextInt();
            // dados enviados
            saida.writeDouble(valor);
            saida.writeDouble(taxa);
            saida.writeInt(periodo);
            sc.close();
            System.out.println("Dados enviados ao servidor");
            // dados recebidos
            List<Parcela> prestacoes = (List<Parcela>) entrada.readObject();
            System.out.println("Dados recebidos do servidor");
            System.out.println("Empréstimo calculado");
            prestacoes.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException ioe) {
            System.out.println("Erro: " + ioe.getMessage());
        }
    }
}
