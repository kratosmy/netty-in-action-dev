package nia.chapter1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import lombok.SneakyThrows;

public class ClientExample {
    private Socket clientSocket;
    private PrintWriter out;
    private OutputStream outputStream;
    private BufferedReader in;

    @SneakyThrows
    public void startConnection(String ip, int port) {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        outputStream = clientSocket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @SneakyThrows
    public String sendMessage(String message) {
        out.println(message);
        outputStream.write(message.getBytes());
        return in.readLine();
    }

    @SneakyThrows
    public void close() {
        in.close();
        out.close();
        clientSocket.close();
    }

    @SneakyThrows
    public static void main(String[] args) {
        ClientExample clientExample = new ClientExample();
        clientExample.startConnection("127.0.0.1", 8888);
        for (int i = 0; i < 10; i++) {
            String response = clientExample.sendMessage("Hello");
            System.out.println(response);
        }
        String response = clientExample.sendMessage("Done");
        System.out.println(response);
    }
}
