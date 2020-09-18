package ga.abzzezz.hangman.server;


import ga.abzzezz.hangman.server.packet.PacketManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread {

    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final PacketManager packetManager;

    public ClientHandler(final Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.packetManager = new PacketManager(this);
    }

    @Override
    public void run() {
        try {
            handle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }

    private void handle() throws IOException {
        Logger.getAnonymousLogger().log(Level.INFO, "Connected to client");
        while (true) {
            while (reader.ready()) {
                packetManager.handlePacket(reader.readLine(), writer);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }
}