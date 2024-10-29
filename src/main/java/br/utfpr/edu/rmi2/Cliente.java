package br.utfpr.edu.rmi2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {
        try {
            IMetodosRemotos stub = (IMetodosRemotos) Naming.lookup("rmi://127.0.0.1/metodoRMI");

            System.out.println("Executando metodo no servidor: " + stub.exibeValor(1000));
        } catch (RemoteException ex) {
            Logger.getLogger(br.utfpr.edu.rmi.Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(br.utfpr.edu.rmi.Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
