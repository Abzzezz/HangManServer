package ga.abzzezz.hangman.rooms;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomManager {

    private final List<Room> rooms = new ArrayList<>();

    public void addRoom(final Room room) {
        rooms.add(room);
        Logger.getAnonymousLogger().log(Level.INFO, "New room created: " + room.getRoomId());
    }

    public Optional<Room> getRoomById(final String roomId) {
        return rooms.parallelStream().filter(room -> room.getRoomId().equals(roomId)).findAny();
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
