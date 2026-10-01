import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private String username;

    private List<ClientHandler> clients;

    public ClientHandler(
            Socket socket,
            List<ClientHandler> clients) {

        this.socket = socket;
        this.clients = clients;

        try {

            reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );

            writer = new PrintWriter(
                    socket.getOutputStream(),
                    true
            );

        } catch (IOException e) {

            System.out.println(
                    "Error creating client handler: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public void run() {

        try {

            // Ask the client for a username
            writer.println(
                    "Enter your username:"
            );

            username = reader.readLine();

            if (username == null || username.trim().isEmpty()) {

                username = "Anonymous";
            }

            System.out.println(
                    username + " joined the chat."
            );

            writer.println(
                    "Welcome, " + username + "!"
            );

            writer.println(
                    "Type your messages below."
            );

            // Inform other clients
            ChatServer.broadcast(
                    username + " joined the chat.",
                    this,
                    clients
            );

            String message;

            // Continuously receive messages
            while ((message = reader.readLine()) != null) {

                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }

                String formattedMessage =
                        username + ": " + message;

                System.out.println(formattedMessage);

                ChatServer.broadcast(
                        formattedMessage,
                        this,
                        clients
                );
            }

        } catch (IOException e) {

            System.out.println(
                    username + " disconnected."
            );

        } finally {

            ChatServer.removeClient(
                    this,
                    clients
            );

            ChatServer.broadcast(
                    username + " left the chat.",
                    this,
                    clients
            );

            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(
                        "Error closing socket."
                );
            }
        }
    }

    // Sends a message to this particular client
    public void sendMessage(String message) {

        if (writer != null) {
            writer.println(message);
        }
    }
}
