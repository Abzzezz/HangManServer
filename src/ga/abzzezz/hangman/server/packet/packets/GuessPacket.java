package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.server.packet.Packet;
import ga.abzzezz.hangman.server.packet.PacketManager;

import java.util.Optional;
import java.util.UUID;

public class GuessPacket extends Packet {

    public GuessPacket(final PacketManager parent) {
        super("GUESS_LETTER", parent);
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
        Main.ROOM_MANAGER.getRoomById(input).ifPresent(room -> room.getPlayerById(UUID.fromString(getAdditionalData().getString("player_identification"))).ifPresent(player -> room.check(Character.toLowerCase((char) getAdditionalData().getInt("letter")), player)));
    }
}
