import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("          CHAT CLIENT");
        System.out.println("======================================");

        try {

            Socket socket = new Socket(
                    SERVER_ADDRESS,
                    SERVER_PORT
            );

            System.out.println(
                    "Connected to chat server."
            );

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            PrintWriter writer =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true
                    );

            Scanner scanner =
                    new Scanner(System.in);

            // Thread for receiving messages
            Thread receiveThread = new Thread(() -> {

                try {

                    String message;

                    while ((message =
                            reader.readLine()) != null) {

                        System.out.println(
                                "\n" + message
                        );

                        System.out.print("> ");
                    }

                } catch (IOException e) {

                    System.out.println(
                            "\nDisconnected from server."
                    );
                }
            });

            receiveThread.start();

            // Main thread sends messages
            while (true) {

                String message =
                        scanner.nextLine();

                writer.println(message);

                if (message.equalsIgnoreCase("/exit")) {

                    break;
                }
            }

            socket.close();
            scanner.close();

            System.out.println(
                    "Disconnected from chat."
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to connect to server."
            );

            System.out.println(
                    "Make sure ChatServer is running first."
            );
        }
    }
}
