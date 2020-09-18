package ga.abzzezz.hangman.server.packet;

import ga.abzzezz.hangman.server.ClientHandler;
import ga.abzzezz.hangman.server.packet.packets.*;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacketManager {

    private final List<Packet> packets = new ArrayList<>();
    private final ClientHandler client;

    public PacketManager(final ClientHandler client) {
        this.client = client;
        packets.add(new CreateRoomPacket(client));
        packets.add(new JoinRoomPacket(client));
        packets.add(new PlayerJoinPacket(client));
        packets.add(new SendWordPacket(client));
        packets.add(new GuessPacket(client));
        packets.add(new PlayerUpdatePacket(client));
    }

    /**
     * Handle Packet based on input string and if needed: respond
     *
     * @param readLine    read line
     * @param printWriter print stream to print response to
     */
    public void handlePacket(final String readLine, final PrintWriter printWriter) {
        final JSONObject receivedPacketJson = new JSONObject(readLine);
        this.getPacket(receivedPacketJson.getString(PacketFormatter.PACKET_KEY)).ifPresent(foundPacket -> {
            final String message = receivedPacketJson.getString(PacketFormatter.MESSAGE_KEY);

            final boolean extraAvailable = receivedPacketJson.getBoolean(PacketFormatter.EXTRA_DATA_AVAILABLE_KEY);
            if (extraAvailable)
                foundPacket.setAdditionalData(receivedPacketJson.getJSONObject(PacketFormatter.EXTRA_DATA_KEY));

            foundPacket.receive(message);

            foundPacket.respond(message).ifPresent(responseString -> {
                printWriter.println(PacketFormatter.formatPacket(foundPacket, responseString));
                printWriter.flush();
            });
        });
    }

    /**
     * Send specific packet through print-stream
     *
     * @param packet      Packet to send
     */
    public void sendPacket(final Packet packet) {
        packet.send().ifPresent(sendString -> {
            client.getWriter().println(PacketFormatter.formatPacket(packet, sendString));
            client.getWriter().flush();
        });
    }

    private Optional<Packet> getPacket(final String packetId) {
        return packets.stream().filter(packet -> packet.getPacketId().equals(packetId)).findFirst();
    }
}
