package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.server.ClientHandler;
import ga.abzzezz.hangman.server.packet.Packet;

import java.util.Optional;

public class GuessPacket extends Packet {

    public GuessPacket(final ClientHandler parent) {
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
        Main.ROOM_MANAGER.getRoomById(input).ifPresent(room -> room.getPlayerByClient(getParent()).ifPresent(player -> room.check((char) getAdditionalData().getInt("letter"), player)));
    }
}
