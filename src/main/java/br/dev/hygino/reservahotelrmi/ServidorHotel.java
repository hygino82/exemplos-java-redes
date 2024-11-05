package br.dev.hygino.reservahotelrmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class ServidorHotel extends UnicastRemoteObject implements IGerenciadorQuartos {

    private static Registry servidorRegistro;
    private List<Quarto> listaQuartos;
    private List<Reserva> listaReservas;
    private int contadorQuartos = 0;

    private List<Quarto> cadastrarQuartos() {
        List<Quarto> quartos = new ArrayList<>();
        IntStream.rangeClosed(1, 10)
                .forEach(q -> quartos.add(new Quarto(0, ++contadorQuartos))); // 10 quartos tipo 0
        IntStream.rangeClosed(1, 20)
                .forEach(q -> quartos.add(new Quarto(1, ++contadorQuartos))); // 20 quartos tipo 1
        IntStream.rangeClosed(1, 5)
                .forEach(q -> quartos.add(new Quarto(2, ++contadorQuartos))); // 5 quartos tipo 2
        IntStream.rangeClosed(1, 3)
                .forEach(q -> quartos.add(new Quarto(3, ++contadorQuartos))); // 3 quartos tipo 3
        IntStream.rangeClosed(1, 2)
                .forEach(q -> quartos.add(new Quarto(4, ++contadorQuartos))); // 2 quartos tipo 4
        return quartos;
    }

    public ServidorHotel() throws RemoteException {
        super();
        listaQuartos = cadastrarQuartos();
        listaReservas = new ArrayList<>();
    }

    @Override
    public void ocuparQuarto(Quarto quarto, String nome) throws RemoteException {
        quarto.setOcupado(true);
        listaReservas.add(new Reserva(nome, quarto, LocalDateTime.now()));
    }

    @Override
    public List<Quarto> exibirQuartos() {
        return listaQuartos;
    }

    @Override
    public void desocuparQuarto(Reserva reserva) {
        reserva.quarto().setOcupado(false);
        listaReservas.remove(reserva);
    }

    @Override
    public List<Reserva> exibirReservas() throws RemoteException {
        return listaReservas;
    }

    @Override
    public List<Quarto> exibirQuartosDesocupados() throws RemoteException {
        return listaQuartos.stream()
                .filter(q -> !q.isOcupado())
                .toList();
    }

    public static void main(String[] args) {
        try {
            servidorRegistro = LocateRegistry.createRegistry(1099);
            Naming.rebind("hotel", new ServidorHotel());
            System.out.println("Aguardando requisicoes...");
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(ServidorHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
