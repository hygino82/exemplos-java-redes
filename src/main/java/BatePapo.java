
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class BatePapo extends Thread {

    private static String usuario = null;
    private static InetAddress endereco;
    private static int porta;

    @Override
    public void run() {
        //receber mensagens dos demais usuários
        try {
            byte[] msg = new byte[128];

            MulticastSocket conexao = new MulticastSocket(porta);

            conexao.joinGroup(new InetSocketAddress(endereco, porta),
                    NetworkInterface.getByInetAddress(endereco));
            while (true) {
                DatagramPacket datagrama = new DatagramPacket(msg, msg.length);
                conexao.receive(datagrama);

                String mensagem = new String(datagrama.getData());

                if (!mensagem.contains(usuario + " diz: ")) {
                    System.out.println('\n' + mensagem);
                    System.out.print("Digite a mensagem: ");
                }
                msg = new byte[128];
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Os parâmetros estão incorretos!");
            System.out.println("javaBatepapo <endereço_multicast> <porta>");
            System.exit(1);//avisa que houve um erro na execução e termina
        }
        try {
            endereco = InetAddress.getByName(args[0]);
            porta = Integer.parseInt(args[1]);

            BatePapo batePapo = new BatePapo();
            batePapo.start();
            //enviar mensagens para os demais usuários
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Digite o seu nome: ");
            usuario = teclado.readLine();

            MulticastSocket conexao = new MulticastSocket();

            conexao.joinGroup(new InetSocketAddress(endereco, porta),
                    NetworkInterface.getByInetAddress(endereco));
            byte[] msg = new byte[128];

            while (true) {
                System.out.print("Digite a mensagem: ");
                String mensagem = teclado.readLine();

                if (mensagem.equalsIgnoreCase("sair")) {
                    System.exit(0);//saiu em erros
                }

                mensagem = usuario + " diz: " + mensagem;

                msg = mensagem.getBytes();

                DatagramPacket datagrama = new DatagramPacket(msg, msg.length, endereco, porta);

                conexao.send(datagrama);
            }
            //teclado.close();
        } catch (IOException | NumberFormatException ex) {
            System.out.println("");
        }
    }
}
