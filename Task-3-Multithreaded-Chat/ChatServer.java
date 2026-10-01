import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static final int PORT = 5000;

    // Stores all connected clients
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("       MULTITHREADED CHAT SERVER");
        System.out.println("======================================");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started successfully.");
            System.out.println("Waiting for clients on port " + PORT + "...");

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println(
                        "New client connected: "
                                + socket.getInetAddress()
                );

                ClientHandler clientHandler =
                        new ClientHandler(socket, clients);

                synchronized (clients) {
                    clients.add(clientHandler);
                }

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }

        } catch (IOException e) {

            System.out.println(
                    "Server error: " + e.getMessage()
            );
        }
    }

    // Sends a message to every connected client
    public static void broadcast(
            String message,
            ClientHandler sender,
            List<ClientHandler> clients) {

        synchronized (clients) {

            for (ClientHandler client : clients) {

                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    // Removes a client when they disconnect
    public static void removeClient(
            ClientHandler client,
            List<ClientHandler> clients) {

        synchronized (clients) {
            clients.remove(client);
        }

        System.out.println("A client disconnected.");
    }
}
