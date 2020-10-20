package ga.abzzezz.hangman;

import ga.abzzezz.hangman.rooms.RoomManager;
import ga.abzzezz.hangman.server.Server;
import ga.abzzezz.hangman.util.QuickLog;

public class Main {

    public static final RoomManager ROOM_MANAGER = new RoomManager();

    public static void main(final String[] args) {
        QuickLog.log("Server started on port: " + args[0], QuickLog.LogType.INFO);
        new Server(Integer.parseInt(args[0])).startServer();
    }
}
