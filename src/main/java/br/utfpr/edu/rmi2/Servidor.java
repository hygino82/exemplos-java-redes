package br.utfpr.edu.rmi2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends UnicastRemoteObject implements IMetodosRemotos {

    @Override
    public String exibeValor(int valor) throws RemoteException {
        return "\nValor recebido pelo servidor: " + valor;
    }

    public Servidor() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            Registry servidorRegistro = LocateRegistry.createRegistry(1099);
            Naming.rebind("metodoRMI", new Servidor());
            System.out.println("Aguardando requisicoes...");
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
