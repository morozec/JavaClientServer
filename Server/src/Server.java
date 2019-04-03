import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by moro2609 on 03.04.2019.
 */
public class Server {
    public static void main(String[] args)
    {
        System.out.println("Server started");
        BufferedReader in = null;
        PrintWriter out = null;
        ServerSocket server = null;
        Socket fromClient = null;

        try {
            server = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't listen to port 123");
            System.exit(-1);
        }


        try {
            System.out.println("Waiting for a client...");
            fromClient = server.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't accept client");
            System.exit(-1);
        }

        try {
            in = new BufferedReader(new InputStreamReader(fromClient.getInputStream()));
            out = new PrintWriter(fromClient.getOutputStream(), true);//флаг autoFlush установлен в true - сообщения будут отправляться сразу после записи в out

            System.out.println("Wait for messages");
            String input = in.readLine();
            while (input != null) {
                if (input.equals("exit")) {
                    System.out.println("Client sent exit command");
                    break;
                }
                System.out.println(input);
                out.println(input + " accepted");//отправляем ответ клиенту
                System.out.println("Reply sent");
                input = in.readLine(); //читаем новое сообщение от клиента
            }

            in.close();
            out.close();
            fromClient.close();
            server.close();
            System.out.println("Server is closed");

        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Client socket read/write problem");
        }

    }

}
