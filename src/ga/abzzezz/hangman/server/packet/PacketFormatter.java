package ga.abzzezz.hangman.server.packet;

import org.json.JSONObject;

public class PacketFormatter {


    public static final String PACKET_KEY = "pkg";
    public static final String MESSAGE_KEY = "pkg_msg";
    public static final String EXTRA_DATA_KEY = "ex";
    public static final String EXTRA_DATA_AVAILABLE_KEY = "exAv";

    public static String formatPacket(final Packet packet, final String message) {
        return new JSONObject().put(PACKET_KEY, packet.getPacketId()).put(MESSAGE_KEY, message).put(EXTRA_DATA_KEY, packet.getMoreData()).put(EXTRA_DATA_AVAILABLE_KEY, packet.getMoreData() != null).toString() + "\r\n";
    }


}
