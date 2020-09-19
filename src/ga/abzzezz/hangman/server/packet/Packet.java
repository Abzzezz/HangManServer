package ga.abzzezz.hangman.server.packet;

import org.json.JSONObject;

import java.util.Optional;

public abstract class Packet {

    //The packets id
    private final String packetId;
    private final PacketManager parent;
    //Packets own json
    protected JSONObject moreData;
    //JSON object to source additional data from
    private JSONObject additionalData;


    /**
     * Packet id constructor with given packet identifier
     *
     * @param packetId packet id
     */
    public Packet(final String packetId, final PacketManager parent) {
        this.packetId = packetId;
        this.moreData = new JSONObject();
        this.parent = parent;
    }

    /**
     * Respond to received input
     *
     * @param input received input
     * @return Optional JSON response
     */
    public abstract Optional<String> respond(final String input);

    public abstract Optional<String> send();

    public abstract void receive(final String input);

    public JSONObject getMoreData() {
        return moreData;
    }

    public void setMoreData(JSONObject moreData) {
        this.moreData = moreData;
    }

    public JSONObject getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(JSONObject additionalData) {
        this.additionalData = additionalData;
    }

    public String getPacketId() {
        return packetId;
    }

    public PacketManager getParent() {
        return parent;
    }
}
