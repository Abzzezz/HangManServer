package ga.abzzezz.hangman.rooms;

import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.server.packet.packets.PlayerJoinPacket;
import ga.abzzezz.hangman.server.packet.packets.PlayerUpdatePacket;
import ga.abzzezz.hangman.server.packet.packets.SendWordPacket;
import ga.abzzezz.hangman.server.packet.packets.WordRevealPacket;
import ga.abzzezz.hangman.util.QuickLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Room {
    /**
     * Room's id - determined by the current milis mod 1000
     */
    private final int roomId;
    /**
     * List of joined players. Player is removed if he disconnects
     */
    private final List<Player> players = new ArrayList<>();
    /**
     * Word that is being guessed (always lowercase)
     */
    private String word;
    /**
     * The censored string of the word
     */
    private String wordCensored;
    /**
     * Tries left
     */
    private int triesLeft;
    private Player wordChooser;

    public Room(final int roomId) {
        this.roomId = roomId;
    }

    /**
     * Add new Player to list and send a join packet to all players
     *
     * @param newPlayer new Player to be added
     */
    public void join(final Player newPlayer) {
        players.add(newPlayer);

        getPlayers().forEach(otherPlayer -> otherPlayer.getPacketManager().sendPacket(new PlayerJoinPacket(newPlayer, otherPlayer.getPacketManager())));
        if (newPlayer.getPlayerName().equals("Ali")) reset();
    }

    /**
     * Choose a new word chooser and reset tries
     */
    public void reset() {
        final Player newSelector = players.get(ThreadLocalRandom.current().nextInt(players.size()));
        newSelector.getPacketManager().sendPacket(new SendWordPacket());
        setWordChooser(newSelector);
        triesLeft = 11;
    }

    /**
     * @param character character to be checked
     * @param submitter player who submitted the character
     */
    public void check(final char character, final Player submitter) {
        if (submitter == wordChooser) return;

        if (triesLeft < 1) {
            wordChooser.setPlayerScore(wordChooser.getPlayerScore() + 1);
            revealWord();
            updatePlayer(wordChooser);
            reset();
            return;
        }

        if (word.chars().noneMatch(value -> value == character)) {
            triesLeft--;
        } else {
            final StringBuilder builder = new StringBuilder(wordCensored);
            for (int i = 0; i < word.toCharArray().length; i++) {
                if (word.charAt(i) == character) {
                    builder.setCharAt(i, character);
                }
            }
            wordCensored = builder.toString();
        }

        if (word.equals(wordCensored)) {
            submitter.setPlayerScore(submitter.getPlayerScore() + 1);
            revealWord();
            updatePlayer(submitter);
            reset();
            return;
        }
        revealWord();
    }

    /**
     * Remove player from current room. If room is empty it is removed
     *
     * @param player player to remove
     */
    public void removePlayer(final Player player) {
        players.remove(player);

        if (players.isEmpty()) {
            Main.ROOM_MANAGER.getRooms().remove(this);
            QuickLog.log("Closing room, no players left", QuickLog.LogType.INFO);
        } else updatePlayer(player);
    }

    /**
     * Send a update packet for a specific player to all players
     *
     * @param player player to be updated
     */
    public void updatePlayer(final Player player) {
        players.forEach(allPlayers -> allPlayers.getPacketManager().sendPacket(new PlayerUpdatePacket(player)));
    }

    /**
     * The censored word is send to all players
     */
    public void revealWord() {
        players.forEach(player -> player.getPacketManager().sendPacket(new WordRevealPacket(wordCensored, triesLeft)));
    }

    /**
     * set the current word to be guessed
     * Guessed word will always be lowercase
     *
     * @param word word to set to
     */
    public void setWord(final String word) {
        this.word = word.toLowerCase().trim();
        System.out.println(word);
        this.wordCensored = word.replaceAll(".", "_");
        revealWord();
    }

    /**
     * Filter all player by their uuid
     *
     * @param searchId UUID to search for
     * @return optional player
     */
    public Optional<Player> getPlayerById(final UUID searchId) {
        for (final Player player : players) {
            if (player.getPlayerId().equals(searchId)) return Optional.of(player);
        }
        return Optional.empty();
    }

    public int getRoomId() {
        return roomId;
    }

    public void setWordChooser(final Player wordChooser) {
        this.wordChooser = wordChooser;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
