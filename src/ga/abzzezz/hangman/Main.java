package ga.abzzezz.hangman;

import ga.abzzezz.hangman.rooms.RoomManager;
import ga.abzzezz.hangman.server.Server;

public class Main {

    public static final RoomManager ROOM_MANAGER = new RoomManager();

    public static void main(String[] args) {
        new Server().startServer();
    }
}
