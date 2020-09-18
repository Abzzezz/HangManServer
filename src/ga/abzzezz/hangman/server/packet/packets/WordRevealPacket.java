package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.server.ClientHandler;
import ga.abzzezz.hangman.server.packet.Packet;

import java.util.Optional;

public class WordRevealPacket extends Packet {

    public WordRevealPacket(final ClientHandler parent) {
        super("WORD_REVEAL", parent);
    }

    private String word;
    private int tries;

    public WordRevealPacket(final ClientHandler parent, final String word, final int tries) {
        super("WORD_REVEAL", parent);
        this.word = word;
        this.tries = tries;
    }

    @Override
    public Optional<String> respond(final String input) {
        return Optional.empty();
    }

    @Override
    public Optional<String> send() {
        getMoreData().put("tries", tries);
        return Optional.of(word);
    }

    @Override
    public void receive(final String input) {

    }
}
