package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.rooms.Room;
import ga.abzzezz.hangman.server.packet.Packet;
import ga.abzzezz.hangman.server.packet.PacketManager;

import java.util.Optional;
import java.util.UUID;

public class DisconnectPacket extends Packet {

    public DisconnectPacket(final PacketManager parent) {
        super("DISCONNECT", parent);
    }

    @Override
    public Optional<String> respond(String input) {
        return Optional.empty();
    }

    @Override
    public Optional<String> send() {
        return Optional.empty();
    }

    @Override
    public void receive(final String input) {
        final Optional<Room> room = Main.ROOM_MANAGER.getRoomById(Integer.parseInt(input));
        room.ifPresent(room1 -> room1.getPlayerById(UUID.fromString(getAdditionalData().getString("player_identification"))).ifPresent(room1::removePlayer));
    }
}
