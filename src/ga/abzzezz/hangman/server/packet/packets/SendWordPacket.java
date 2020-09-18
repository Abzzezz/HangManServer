package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.rooms.Room;
import ga.abzzezz.hangman.server.ClientHandler;
import ga.abzzezz.hangman.server.packet.Packet;

import java.util.Optional;

public class SendWordPacket extends Packet {

    public SendWordPacket(final ClientHandler parent) {
        super("WORD_SELECT", parent);
    }

    @Override
    public Optional<String> respond(String input) {
        return Optional.empty();
    }

    @Override
    public Optional<String> send() {
        return Optional.of("NULL");
    }

    @Override
    public void receive(final String input) {
        final Optional<Room> optionalRoom = Main.ROOM_MANAGER.getRoomById(input);
        optionalRoom.ifPresent(room -> room.setWord(getAdditionalData().getString("chosen_word")));
    }
}
