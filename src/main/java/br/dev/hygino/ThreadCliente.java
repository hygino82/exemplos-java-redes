package br.dev.hygino;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class ThreadCliente implements Runnable {

    private final JTextArea txtResultado;
    private final double valor;
    private final double taxa;
    private final int periodo;

    private DataOutputStream saida;
    private ObjectInputStream entrada;

    public ThreadCliente(JTextArea txtResultado, double valor, double taxa, int periodo) {
        this.txtResultado = txtResultado;
        this.valor = valor;
        this.taxa = taxa;
        this.periodo = periodo;
    }

    @Override
    public void run() {
        try (Socket conexao = new Socket()) {
            saida = new DataOutputStream(conexao.getOutputStream());
            entrada = new ObjectInputStream(conexao.getInputStream());

            saida.writeDouble(valor);
            saida.writeDouble(taxa);
            saida.writeInt(periodo);

            List<Parcela> parcelas = (List<Parcela>) entrada.readObject();
        } catch (IOException e) {
            System.out.println("Erro");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
