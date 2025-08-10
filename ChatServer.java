import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The main server class for the chat application.
 * It listens for incoming client connections and spawns a new thread
 * (ClientHandler) for each connected client to handle them concurrently.
 */
public class ChatServer {
    private static final int PORT = 12345;
    // A thread-safe list to keep track of all client handlers.
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        System.out.println("Chat Server is starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                // Wait for a client to connect. This is a blocking call.
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new handler for this client and start it in a new thread.
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Broadcasts a message to all connected clients.
     * @param message The message to broadcast.
     * @param sender The client who sent the message (to avoid sending it back to them).
     */
    public static void broadcastMessage(String message, ClientHandler sender) {
        // Synchronize on the clients list to prevent modification while iterating.
        synchronized (clients) {
            for (ClientHandler client : clients) {
                // Send the message to every client except the one who sent it.
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    /**
     * Removes a client from the list of connected clients.
     * @param client The client handler to remove.
     */
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client disconnected: " + client.getUserName());
    }
}
