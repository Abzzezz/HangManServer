package ga.abzzezz.hangman.rooms;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class RoomManager {

    private final Set<Room> rooms = new CopyOnWriteArraySet<>();

    public void addRoom(final Room room) {
        rooms.add(room);
    }

    public Optional<Room> getRoomById(final int roomId) {
        return rooms.parallelStream().filter(room -> room.getRoomId() == roomId).findAny();
    }

    public Set<Room> getRooms() {
        return rooms;
    }
}
