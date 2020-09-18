package ga.abzzezz.hangman.server.packet.packets;

import ga.abzzezz.hangman.rooms.Player;
import ga.abzzezz.hangman.server.ClientHandler;
import ga.abzzezz.hangman.server.packet.Packet;
import org.json.JSONObject;

import java.util.Optional;

public class PlayerUpdatePacket extends Packet {

    private Player player;

    public PlayerUpdatePacket(final ClientHandler parent) {
        super("PLAYER_UPDATE", parent);
    }

    public PlayerUpdatePacket(final ClientHandler parent, final Player player) {
        super("PLAYER_UPDATE", parent);
        this.player = player;
    }

    @Override
    public Optional<String> respond(final String input) {
        return Optional.empty();
    }

    @Override
    public Optional<String> send() {
        final JSONObject playerObject = new JSONObject().put("player_name", player.getPlayerName()).put("player_score", player.getPlayerScore());
        getMoreData().put("player", playerObject);
        return Optional.of("UPDATE");
    }

    @Override
    public void receive(final String input) {

    }
}
