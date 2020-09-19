package ga.abzzezz.hangman.rooms;

import ga.abzzezz.hangman.server.packet.PacketManager;

import java.util.UUID;

public class Player {

    private final String playerName;
    private final UUID playerId;
    private int playerScore;
    private final PacketManager packetManager;

    public Player(final String playerName, final UUID playerId, final PacketManager packetManager) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.packetManager = packetManager;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

}
