package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.server.packet.Packet;
import ga.abzzezz.hangman.server.packet.PacketManager;

import java.util.Optional;

public class WordRevealPacket extends Packet {

    private String word;
    private int tries;

    public WordRevealPacket(final PacketManager parent) {
        super("WORD_REVEAL", parent);
    }

    public WordRevealPacket(final PacketManager parent, final String word, final int tries) {
        super("WORD_REVEAL", parent);
        this.word = word;
        this.tries = tries;
    }

    public WordRevealPacket(final String word, final int tries) {
        super("WORD_REVEAL");
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
