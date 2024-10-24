package Ficha8.Ex1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;

public class QuoteServer {
    private final static String fullFileName = System.getProperty("user.dir") + "\\src\\Ficha8\\Ex1\\phrases.txt";
    private final static String finalMessage = "Não há mais frases. Adeus!";
    public static void main(String[] args) {
        BufferedReader br = null;
        String phrase = new Date().toString();

        try {
            br = new BufferedReader(new FileReader(fullFileName));
            phrase = getNextQuote(br);
        } catch (FileNotFoundException e) {
            System.err.println("Não foi possível abrir o ficheiro em " +fullFileName);
        }

        //Buffer
        byte[] buffer = new byte[256];
        //UDP packet
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        try (
                DatagramSocket socket = new DatagramSocket(4322);
                ) {

            boolean exit = false;
            while (!phrase.equals(finalMessage) && !exit) {
                if (br != null) {
                    phrase = getNextQuote(br);
                } else {
                    exit = true;
                }

                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                buffer = phrase.getBytes();

                packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);
            }


        } catch (SocketException e) {
            System.err.println("Erro ao tentar abrir o socket na porta 4322");
            System.exit(2);
        } catch (IOException e) {
            System.err.println("Erro ao receber o pacote do cliente");
            System.exit(3);
        }
    }

    private static String getNextQuote(BufferedReader br) {

        String line;
        try {
            if ((line = br.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar o ficheiro");
        }

        return finalMessage;
    }


}
