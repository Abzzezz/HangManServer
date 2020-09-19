package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.rooms.Player;
import ga.abzzezz.hangman.rooms.Room;
import ga.abzzezz.hangman.server.packet.Packet;
import ga.abzzezz.hangman.server.packet.PacketManager;

import java.util.Optional;
import java.util.UUID;

public class JoinRoomPacket extends Packet {

    /**
     * Packet id constructor with given packet identifier
     */
    public JoinRoomPacket(final PacketManager parent) {
        super("ROOM_JOIN", parent);
    }

    @Override
    public Optional<String> respond(final String input) {
        final Player player = new Player(getAdditionalData().getString("player_name"), UUID.fromString(getAdditionalData().getString("player_identification")), getParent());
        final Optional<Room> room = Main.ROOM_MANAGER.getRoomById(input);

        if (room.isPresent()) {
            room.get().join(player);
            return Optional.of("200");
        } else return Optional.of("404");
    }

    @Override
    public Optional<String> send() {
        return Optional.empty();
    }

    @Override
    public void receive(String input) {

    }
}
