import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles communication for a single client connected to the server.
 * This class runs in its own thread.
 */
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String userName;
    private static int userCount = 0;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        // Assign a unique username to this client.
        this.userName = "User" + (++userCount);
    }

    @Override
    public void run() {
        try {
            // Setup streams for communication.
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Welcome the new user and inform others.
            out.println("Welcome! You are " + userName);
            ChatServer.broadcastMessage(userName + " has joined the chat.", this);

            String inputLine;
            // Read messages from the client and broadcast them.
            while ((inputLine = in.readLine()) != null) {
                String message = "[" + userName + "]: " + inputLine;
                System.out.println("Received from " + userName + ": " + inputLine);
                ChatServer.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            // This often happens when a client disconnects unexpectedly.
            System.out.println(userName + " has disconnected.");
        } finally {
            // Cleanup: remove client and close resources.
            ChatServer.removeClient(this);
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a message to this specific client.
     * @param message The message to send.
     */
    public void sendMessage(String message) {
        out.println(message);
    }

    public String getUserName() {
        return userName;
    }
}
