package ga.abzzezz.hangman.rooms;

import ga.abzzezz.hangman.server.ClientHandler;

public class Player {

    private final String playerName;
    private final ClientHandler playerHandler;
    private int playerScore;

    public Player(final String playerName, final ClientHandler playerHandler) {
        this.playerName = playerName;
        this.playerHandler = playerHandler;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ClientHandler getPlayerHandler() {
        return playerHandler;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

}
