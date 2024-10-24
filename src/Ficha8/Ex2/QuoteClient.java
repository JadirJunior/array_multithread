package Ficha8.Ex2;

import java.io.IOException;
import java.net.*;

public class QuoteClient {
    public static void main(String[] args) {
        try (
                DatagramSocket socket = new DatagramSocket();
                ){
            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address ,4322);
            socket.send(packet);

            socket.receive(packet);

            String received = new String(packet.getData());

            System.out.println(received);

        } catch (SocketException e) {
            System.err.println("Erro ao tentar criar o socket");
        } catch (UnknownHostException e) {
            System.err.println("Servidor não encontrado");
        } catch (IOException e) {
            System.err.println("Erro ao enviar o pacote");
        }
    }
}
