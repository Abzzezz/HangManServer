package ga.abzzezz.hangman.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private ServerSocket server;
    private boolean running;

    /**
     * Start server on default port: 1010
     */
    public Server() {
        try {
            this.server = new ServerSocket(1010);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start new server & await clients
     */
    public void startServer() {
        Logger.getAnonymousLogger().log(Level.INFO, "Starting up server");
        if (isRunning()) throw new IllegalStateException("Server already running");
        running = true;

        while (running) {
            try {
                new ClientHandler(server.accept()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stop server
     * @throws IOException if server socket does not close properly
     */
    public void stopServer() throws IOException {
        if (!isRunning()) throw new IllegalStateException("Server already idle");
        running = false;
        server.close();
    }

    public boolean isRunning() {
        return running;
    }

}
