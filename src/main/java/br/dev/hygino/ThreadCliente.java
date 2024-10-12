package br.dev.hygino;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public final class ThreadCliente extends Thread {

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
        try {
            System.out.println("Enviando dados ao Servidor");
            Socket conexao = new Socket("127.0.0.1", 57000);
            saida = new DataOutputStream(conexao.getOutputStream());
            entrada = new ObjectInputStream(conexao.getInputStream());

            saida.writeDouble(valor);
            saida.writeDouble(taxa);
            saida.writeInt(periodo);

            List<Parcela> parcelas = (List<Parcela>) entrada.readObject();
           
                parcelas.forEach(p -> txtResultado.append(p.toString() + '\n'));
          

            conexao.close();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
