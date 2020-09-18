package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.rooms.Player;
import ga.abzzezz.hangman.rooms.Room;
import ga.abzzezz.hangman.server.ClientHandler;
import ga.abzzezz.hangman.server.packet.Packet;

import java.util.Optional;

public class JoinRoomPacket extends Packet {

    /**
     * Packet id constructor with given packet identifier
     */
    public JoinRoomPacket(final ClientHandler parent) {
        super("ROOM_JOIN", parent);
    }

    @Override
    public Optional<String> respond(final String input) {
        final Player player = new Player(getAdditionalData().getString("player_name"), getParent());
        final Optional<Room> room = Main.ROOM_MANAGER.getRoomById(input);

        if (room.isPresent()) {
            room.get().join(player);
            getParent().getPacketManager().sendPacket(new PlayerJoinPacket(player, getParent()));
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
