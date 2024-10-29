package br.utfpr.edu.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor implements IOlaMundo {

    @Override
    public String olaMundo() throws RemoteException {
        return "Ola mundo!";
    }

    public static void main(String[] args) {
        try {
            Registry servidorRegistro = LocateRegistry.createRegistry(1099);
            Servidor servidor = new Servidor();
            IOlaMundo skeleton = (IOlaMundo) UnicastRemoteObject.exportObject(servidor, 0);
            servidorRegistro.bind("olarmi", skeleton);
            System.out.println("Servidor iniciado...");
        } catch (RemoteException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
