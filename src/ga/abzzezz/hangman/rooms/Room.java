package ga.abzzezz.hangman.rooms;

import ga.abzzezz.hangman.server.ClientHandler;
import ga.abzzezz.hangman.server.packet.packets.PlayerUpdatePacket;
import ga.abzzezz.hangman.server.packet.packets.SendWordPacket;
import ga.abzzezz.hangman.server.packet.packets.WordRevealPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room {

    private final String roomId;
    private String word;
    private String wordCensored;

    private int triesLeft = 11;

    private final List<Player> players = new ArrayList<>();
    private Player currentChooser;

    public Room(final String roomId) {
        this.roomId = roomId;
    }

    public void join(final Player player) {
        Logger.getAnonymousLogger().log(Level.INFO, "New player joined, name: " + player.getPlayerName());
        players.add(player);
        if (player.getPlayerName().equals("AliDerBoss")) {
            selectChooser();
        }
    }

    public void selectChooser() {
        final Player wordSelector = players.get(ThreadLocalRandom.current().nextInt(players.size()));
        wordSelector.getPlayerHandler().getPacketManager().sendPacket(new SendWordPacket(wordSelector.getPlayerHandler()));
        setCurrentChooser(wordSelector);
        triesLeft = 11;
    }

    public void check(final char character, final Player submitter) {
        if (triesLeft < 1) {
            currentChooser.setPlayerScore(currentChooser.getPlayerScore() + 1);
            updatePlayer(currentChooser);
            selectChooser();
            return;
        }

        if (word.chars().noneMatch(value -> value == character)) {
            triesLeft--;
        } else {
            char[] chars = word.toCharArray();
            final StringBuilder builder = new StringBuilder(wordCensored);
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == character) {
                    builder.setCharAt(i, character);
                }
            }
            wordCensored = builder.toString();
        }

        if (word.equals(wordCensored)) {
            submitter.setPlayerScore(submitter.getPlayerScore() + 1);
            updatePlayer(submitter);
            selectChooser();
            return;
        }

        sendToPlayers();
    }

    public void updatePlayer(final Player player) {
        players.forEach(player1 -> player1.getPlayerHandler().getPacketManager().sendPacket(new PlayerUpdatePacket(player1.getPlayerHandler(), player)));
    }

    public void sendToPlayers() {
        for (final Player player : players) {
            player.getPlayerHandler().getPacketManager().sendPacket(new WordRevealPacket(player.getPlayerHandler(), wordCensored, triesLeft));
        }
    }

    public String getRoomId() {
        return roomId;
    }
    public void setWord(final String word) {
        this.word = word;
        this.wordCensored = word.replaceAll(".", "_");
        sendToPlayers();
    }

    public Optional<Player> getPlayerByClient(final ClientHandler playerHandler) {
        return players.stream().filter(player -> player.getPlayerHandler().equals(playerHandler)).findAny();
    }

    public void setCurrentChooser(Player currentChooser) {
        this.currentChooser = currentChooser;
    }

}
