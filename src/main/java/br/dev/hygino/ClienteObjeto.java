package br.dev.hygino;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClienteObjeto {
    public static void main(String[] args) {
        try (Socket conexao = new Socket("127.0.0.1", 5555); ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream())) {
            List<Pessoa> pessoas = List.of(
                    new Pessoa("Gorete Medeiros", "142.271.317-45", "goretinha@getmail.com", 45),
                    new Pessoa("Joaquim Silva", "122.451.872-64", "joaquim.silva@mail.com", 13),
                    new Pessoa("Ana Paula Oliveira", "113.358.293-11", "ana.paula@getmail.com", 37),
                    new Pessoa("Carlos Eduardo Martins", "153.167.509-82", "carloseduardo@mail.com", 17),
                    new Pessoa("Marina Souza", "174.923.234-09", "marina.souza@domail.com", 31),
                    new Pessoa("Pedro Henrique Alves", "184.354.667-32", "pedrohenrique@getmail.com", 41),
                    new Pessoa("Sônia Maria Rocha", "125.678.912-10", "soniamaria@domail.com", 49),
                    new Pessoa("Marcelo Gonçalves", "198.253.444-78", "marcelo.goncalves@getmail.com", 35),
                    new Pessoa("Beatriz Ramos", "104.675.320-55", "beatriz.ramos@domail.com", 15),
                    new Pessoa("Fernanda Lopes", "153.612.879-36", "fernanda.lopes@mail.com", 16),
                    new Pessoa("Lucas Braga", "126.472.988-23", "lucas.braga@domail.com", 13),
                    new Pessoa("Cláudia Freitas", "177.341.569-41", "claudia.freitas@getmail.com", 44),
                    new Pessoa("Roberto Tavares", "184.112.456-74", "roberto.tavares@domail.com", 31),
                    new Pessoa("Juliana Macedo", "144.221.378-98", "juliana.macedo@domail.com", 28),
                    new Pessoa("Renato Barros", "193.145.293-25", "renato.barros@mail.com", 20)
            );

            saida.writeObject(pessoas);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
