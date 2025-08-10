import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The client class for the chat application.
 * It connects to the server and has two threads:
 * 1. The main thread for sending user-typed messages.
 * 2. A listener thread for receiving messages from the server.
 */
public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the chat server. Type 'exit' to quit.");

            // Start a new thread to listen for messages from the server.
            Thread listenerThread = new Thread(() -> {
                try {
                    String fromServer;
                    while ((fromServer = in.readLine()) != null) {
                        System.out.println(fromServer);
                    }
                } catch (IOException e) {
                    // This can happen if the server closes the connection.
                    System.out.println("Connection to server lost.");
                }
            });
            listenerThread.start();

            // Main thread handles sending messages from the user.
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                out.println(userInput);
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                SERVER_ADDRESS + ". Is the server running?");
        }
    }
}
