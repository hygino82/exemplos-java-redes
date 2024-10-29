package br.utfpr.edu.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IOlaMundo extends Remote {

    public String olaMundo() throws RemoteException;
}
