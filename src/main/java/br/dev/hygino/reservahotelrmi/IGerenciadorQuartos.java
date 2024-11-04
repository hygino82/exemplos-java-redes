package br.dev.hygino.reservahotelrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IGerenciadorQuartos extends Remote {

    void ocuparQuarto(Quarto quarto, String nome) throws RemoteException;

    List<Quarto> exibirQuartos() throws RemoteException;

    List<Quarto> exibirQuartosDesocupados() throws RemoteException;

    List<Reserva> exibirReservas() throws RemoteException;

    void desocuparQuarto(Reserva reserva) throws RemoteException;
}
