package br.dev.hygino;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServidorObjeto {
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(5555)) {
            Socket conexao = servidor.accept();
            System.out.println("Cliente conectado...");
            ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());

            final List<Pessoa> pessoas = (List<Pessoa>) entrada.readObject();
            pessoas.stream().filter(p -> p.idade() > 18).forEach(System.out::println);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }
}
