package ga.abzzezz.hangman.rooms;

import ga.abzzezz.hangman.Main;
import ga.abzzezz.hangman.server.packet.packets.PlayerJoinPacket;
import ga.abzzezz.hangman.server.packet.packets.PlayerUpdatePacket;
import ga.abzzezz.hangman.server.packet.packets.SendWordPacket;
import ga.abzzezz.hangman.server.packet.packets.WordRevealPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Room {
    /**
     * Room's id - determined by the current milis mod 1000
     */
    private final String roomId;
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
    /**
     * List of joined players. Player is removed if he disconnects
     */
    private final List<Player> players = new ArrayList<>();
    private Player wordChooser;

    public Room(final String roomId) {
        this.roomId = roomId;
    }

    /**
     * Add player to list and send a join packet to all players
     * @param player player to be added
     */
    public void join(final Player player) {
        Logger.getAnonymousLogger().log(Level.INFO, "New player joined, name: " + player.getPlayerName());
        players.add(player);
        getPlayers().forEach(player1 -> player1.getPacketManager().sendPacket(new PlayerJoinPacket(player, player1.getPacketManager())));
        if (player.getPlayerName().equals("Ali")) reset();
    }

    /**
     * Choose a new word chooser and reset tries
     */
    public void reset() {
        final Player wordSelector = players.get(ThreadLocalRandom.current().nextInt(players.size()));
        wordSelector.getPacketManager().sendPacket(new SendWordPacket(wordSelector.getPacketManager()));
        setWordChooser(wordSelector);
        triesLeft = 11;
    }

    /**
     *
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
        } else updatePlayer(player);
    }

    /**
     * Send a update packet for a specific player to all players
     *
     * @param player player to be updated
     */
    public void updatePlayer(final Player player) {
        players.forEach(allPlayers -> allPlayers.getPacketManager().sendPacket(new PlayerUpdatePacket(allPlayers.getPacketManager(), player)));
    }

    /**
     * The censored word is send to all players
     */
    public void revealWord() {
        for (final Player player : players) {
            player.getPacketManager().sendPacket(new WordRevealPacket(player.getPacketManager(), wordCensored, triesLeft));
        }
    }

    /**
     * set the current word to be guessed
     * Guessed word will always be lowercase
     *
     * @param word word to set to
     */
    public void setWord(final String word) {
        this.word = word.toLowerCase();
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
        return players.stream().filter(player -> player.getPlayerId().equals(searchId)).findAny();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setWordChooser(Player wordChooser) {
        this.wordChooser = wordChooser;
    }

    public List<Player> getPlayers() {
        return players;
    }


}
