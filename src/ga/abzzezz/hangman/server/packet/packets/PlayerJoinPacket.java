package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.rooms.Player;
import ga.abzzezz.hangman.server.ClientHandler;
import ga.abzzezz.hangman.server.packet.Packet;
import org.json.JSONObject;

import java.util.Optional;

public class PlayerJoinPacket extends Packet {

    /**
     * Packet id constructor with given packet identifier
     */

    private Player player;

    public PlayerJoinPacket(final ClientHandler parent) {
        super("PLAYER_JOIN", parent);
    }

    public PlayerJoinPacket(final Player player, final ClientHandler parent) {
        super("PLAYER_JOIN", parent);
        this.player = player;
    }

    @Override
    public Optional<String> respond(String input) {
        return Optional.empty();
    }

    @Override
    public Optional<String> send() {
        final JSONObject playerObject = new JSONObject().put("player_name", player.getPlayerName()).put("player_score", player.getPlayerScore());
        getMoreData().put("player_joined", playerObject);
        return Optional.of("JOINED");
    }

    @Override
    public void receive(String input) {

    }
}
