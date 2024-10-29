package br.utfpr.edu.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {
        try {
            Registry servidorRegistro = LocateRegistry.getRegistry("127.0.0.1", 1099);
            IOlaMundo stub = (IOlaMundo) servidorRegistro.lookup("olarmi");
            
            System.out.println("Recebido: " + stub.olaMundo());         
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
