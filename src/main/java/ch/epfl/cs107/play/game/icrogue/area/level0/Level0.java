package ch.epfl.cs107.play.game.icrogue.area.level0;

import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0KeyRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room.Level0Connectors;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0StaffRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0TurretRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0 extends Level {
    private final int PART_1_KEY_ID = 1;
    private final int BOSS_KEY_ID = 3;

    public Level0() {
        super(Level0RoomTypes.getRoomsDistribution());

        // old generators
//        super(4, 2);
//        generateMap2();
//        setStartingRoom(new DiscreteCoordinates(1, 1));
    }

    protected enum Level0RoomTypes {
        TURRET (2),
        STAFF (2),
        KEY (1),
        NORMAL (6),
        SPAWN (1);

        private int count = 0;
        Level0RoomTypes (int count) {
            this.count = count;
        }

        private static int[] getRoomsDistribution () {
            Level0RoomTypes[] types = Level0RoomTypes.values();
            int[] roomsDistribution = new int[types.length];
            for (int i = 0 ; i < types.length; i++) {
                roomsDistribution[i] = types[i].count;
            }
            return roomsDistribution;
        }
    }

    @Override
    protected void setupConnectors(MapState[][] roomsMapped, ICRogueRoom room) {
        // scan neighbors

        DiscreteCoordinates pos = room.getRoomCoordinates();
        for (int i = 0; i < Level0Connectors.values().length; i++) {
            DiscreteCoordinates neighborPos = pos.jump(Level0Connectors.values()[i].getOrientation().toVector());
            // check if neighbourPos is in the map
            if (neighborPos.x >= 0 && neighborPos.x < roomsMapped.length && neighborPos.y >= 0 && neighborPos.y < roomsMapped[0].length) {
                MapState neighborState = roomsMapped[neighborPos.x][neighborPos.y];
                if (neighborState == MapState.CREATED) {
                    setRoomConnectorDest(pos, ("icrogue/level0" + neighborPos.x) + neighborPos.y, Level0Connectors.values()[i]);
                } else if (neighborState == MapState.BOSS_ROOM) {
                    setRoomConnectorDest(pos, ("icrogue/level0" + neighborPos.x) + neighborPos.y, Level0Connectors.values()[i]);
                    lockRoomConnector(pos, Level0Connectors.values()[i], BOSS_KEY_ID);
                }
            }
        }

    }

    @Override
    protected void createRoom(int type, DiscreteCoordinates position) {
        if (type == 4) {
            setRoom(position, new Level0Room(position));
            setStartingRoom(position);
        } else
            switch (type) {
                case -1 -> setRoom(position, new Level0TurretRoom(position)); // boss room is turret room
                case 0 -> setRoom(position, new Level0TurretRoom(position));
                case 1 -> setRoom(position, new Level0StaffRoom(position));
                case 2 -> setRoom(position, new Level0KeyRoom(position, BOSS_KEY_ID));
                case 3 -> setRoom(position, new Level0Room(position));
            }
    }


    // ----------- OLD MANUAL GEN METHODS BELOW --------------------------------
    private void generateMap1() {
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0KeyRoom(room00, PART_1_KEY_ID));
        setRoomConnectorDest(room00, "icrogue/level010", Level0Room.Level0Connectors.E);
        lockRoomConnector(room00, Level0Room.Level0Connectors.E, PART_1_KEY_ID);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnectorDest(room10, "icrogue/level000", Level0Room.Level0Connectors.W);
    }

    private void generateMap2() {
        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0TurretRoom(room00) );
        setRoomConnectorDest(room00, "icrogue/level010", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnectorDest(room10, "icrogue/level011", Level0Room.Level0Connectors.S);
        setRoomConnectorDest(room10, "icrogue/level020", Level0Room.Level0Connectors.E);

        setRoomConnectorDest(room10, "icrogue/level000", Level0Room.Level0Connectors.W);
        lockRoomConnector(room10, Level0Room.Level0Connectors.W, BOSS_KEY_ID);


        DiscreteCoordinates room20 = new DiscreteCoordinates(2, 0);
        setRoom(room20, new Level0StaffRoom(room20));
        setRoomConnectorDest(room20, "icrogue/level010", Level0Room.Level0Connectors.W);
        setRoomConnectorDest(room20, "icrogue/level030", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room30 = new DiscreteCoordinates(3, 0);
        setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY_ID));
        setRoomConnectorDest(room30, "icrogue/level020", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom(room11, new Level0Room(room11));
        setRoomConnectorDest(room11, "icrogue/level010", Level0Room.Level0Connectors.N);
    }

}
