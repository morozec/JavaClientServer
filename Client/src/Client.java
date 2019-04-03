import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by moro2609 on 03.04.2019.
 */
public class Client {
    public static void main(String[] args)
    {
        System.out.println("Client started");
        Socket fromServer = null;

        BufferedReader consoleIn = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            fromServer = new Socket("localhost",4444);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't listen to port 4444");
            System.exit(-1);
        }

        consoleIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            in = new BufferedReader(new InputStreamReader(fromServer.getInputStream()));
            out = new PrintWriter(fromServer.getOutputStream(), true);//флаг autoFlush установлен в true - сообщения будут отправляться сразу после записи в out

            String consoleInput = consoleIn.readLine();
            while (consoleInput != null) {
                out.println(consoleInput);
                String serverReply = in.readLine();
                System.out.println(serverReply);

                if (consoleInput.equals("exit")) {
                    System.out.println("Client sent exit command");
                    break;
                }

                consoleInput = consoleIn.readLine();
            }

            consoleIn.close();
            in.close();
            out.close();
            fromServer.close();
            System.out.println("Client is closed");
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Socket read/write problem");
        }

    }
}
