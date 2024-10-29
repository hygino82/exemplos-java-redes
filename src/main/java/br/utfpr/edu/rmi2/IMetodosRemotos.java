package br.utfpr.edu.rmi2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMetodosRemotos extends Remote {

    public String exibeValor(int valor) throws RemoteException;
}
