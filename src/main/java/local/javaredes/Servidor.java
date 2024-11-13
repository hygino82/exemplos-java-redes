package local.javaredes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Servidor implements Runnable {

    private DataOutputStream saida;
    private ObjectInputStream entrada;
    private double media, variancia, desvioPadrao;
    private final Socket socket;

    public Servidor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            entrada = new ObjectInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());

            System.out.println("Aguardando dados do cliente...");
            List<Double> valores = (List<Double>) entrada.readObject();
            System.out.println("Dados recebidos do cliente");

            calcularValores(valores);

            saida.writeDouble(media);
            saida.writeDouble(variancia);
            saida.writeDouble(desvioPadrao);

            System.out.println("Dados processados e enviados ao cliente");

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private synchronized void calcularValores(List<Double> valores) {
        double soma = 0.0;
        
        for (var valor : valores) {
            soma += valor;
        }
        media = soma / valores.size();

        double somaQuadDif = 0.0;
        for (var valor : valores) {
            somaQuadDif += Math.pow(media - valor, 2);
        }
        variancia = somaQuadDif / valores.size();
        desvioPadrao = Math.sqrt(variancia);
    }

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(52000)) {
            System.out.println("Servidor iniciado na porta 52000. Aguardando conexões...");
            while (true) {
                Socket conexao = servidor.accept();
                System.out.println("Cliente conectado: " + conexao.getInetAddress());
                Thread thread = new Thread(new Servidor(conexao));
                thread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
