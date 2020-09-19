package ga.abzzezz.hangman.server.packet.packets;


import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.rooms.Room;
import ga.abzzezz.hangman.server.packet.Packet;
import ga.abzzezz.hangman.server.packet.PacketManager;

import java.util.Optional;

public class CreateRoomPacket extends Packet {

    public CreateRoomPacket(final PacketManager parent) {
        super("ROOM_CREATE", parent);
    }

    @Override
    public Optional<String> respond(String input) {
        final Room room = new Room(String.valueOf(System.currentTimeMillis() % 1000));
        Main.ROOM_MANAGER.addRoom(room);
        return Optional.of(room.getRoomId());
    }

    @Override
    public Optional<String> send() {
        return Optional.of("NULL");
    }

    @Override
    public void receive(String input) {

    }
}
