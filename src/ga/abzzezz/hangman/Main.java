package ga.abzzezz.hangman;

import ga.abzzezz.hangman.rooms.RoomManager;
import ga.abzzezz.hangman.server.Server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static final RoomManager ROOM_MANAGER = new RoomManager();

    public static void main(final String[] args) {
        new Server(Integer.parseInt(args[0])).startServer();
        Logger.getAnonymousLogger().log(Level.INFO, "Starting server on port:" + args[0]);
    }
}
