package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Level is an abstract class that represents any level in the game.
 * It has its rooms as an attribute and handles the random generation of rooms.
 * The specific room types are implemented in each concrete level.
 * Level.java is a signal that is On if the level is completed.
 */
public abstract class Level implements Logic {
    private ICRogueRoom[][] roomsList;
    private DiscreteCoordinates posBossRoom = new DiscreteCoordinates(0, 0);
    private String titleStartRoom;


    /**
     * Minimal constructor for Level which does not place / create any rooms.
     * @param width (int): width of the roomMap
     * @param height (int): height of the roomMap
     */
    protected Level(int width, int height) {
        createRoomsList(width, height);
    }

    /**
     * Constructor for Level leveraging the random room placement.
     * @param roomsDistribution : the distribution of the rooms in the level.
     */
    protected Level(int[] roomsDistribution) {
        randomPlaceGen(roomsDistribution);
    }


    /**
     * Possible states of rooms on the map.
     */
    protected enum MapState {

        NULL, // Empty space
        PLACED, // The room has been placed but not yet explored by the room placement algorithm
        EXPLORED, // The room has been placed and explored by the algorithm
        BOSS_ROOM, // The room is a boss room
        CREATED; // The room has been instantiated in the room map

        @Override
        public String toString() {
            return Integer.toString(ordinal());
        }
    }

    /**
     * Registers all the rooms in the level with the parent AreaGame.
     * @param parent (AreaGame): Owner / parent of the level.
     */
    public void registerRooms(AreaGame parent) {
        for (ICRogueRoom[] rooms : roomsList) {
            for (ICRogueRoom room : rooms) {
                if (room != null) parent.addArea(room);
            }
        }
    }

    /**
     * Returns the title of the level's starting / spawn room.
     * @return (String): the title of the starting room.
     */
    public String getTitleStartRoom() {
        return titleStartRoom;
    }

    /**
     * Creates an empty fixed size list for ICRogueRoom's.
     * @param width (int): width of the roomMap
     * @param height (int): height of the roomMap
     */
    private void createRoomsList(int width, int height) {
        roomsList = new ICRogueRoom[width][height];
    }

    /**
     * Handles the pseudorandom placement of rooms on the level's map.
     * Rooms are mapped, created and connected.
     * @param roomsDistribution : the requested distribution of the rooms in the level.
     */
    private void randomPlaceGen(int[] roomsDistribution) {
        int nbRooms = Arrays.stream(roomsDistribution).sum();
        createRoomsList(nbRooms, nbRooms);
        MapState[][] mappedRooms = mapRooms(nbRooms);
        placeRooms(roomsDistribution, mappedRooms);
        for (ICRogueRoom[] rooms : roomsList)
            for (ICRogueRoom room : rooms)
                if (room != null)
                    setupConnectors(mappedRooms, room);
    }

    /**
     * Places the different rooms pseudorandomly in potential positions found on the map.
     * @param roomsDistribution : the requested distribution of the rooms in the level.
     * @param mappedPlaces : the map of potential places for rooms in the level.
     */
    private void placeRooms(int[] roomsDistribution, MapState[][] mappedPlaces) {
        for (int i = 0; i <= roomsDistribution.length; i++) {
            // non-boss rooms (rooms in the distribution)
            if (i < roomsDistribution.length) {
                // index all rooms that are PLACED or EXPLORED
                ArrayList<DiscreteCoordinates> potentialMapIndexes = new ArrayList<>();
                for (int x = 0; x < mappedPlaces.length; x++)
                    for (int y = 0; y < mappedPlaces[x].length; y++)
                        if ((mappedPlaces[x][y] == MapState.PLACED) || (mappedPlaces[x][y] == MapState.EXPLORED))
                            potentialMapIndexes.add(new DiscreteCoordinates(x, y));
                // place rooms pseudorandomly in available spots
                for (DiscreteCoordinates position : RandomHelper.chooseKInList(roomsDistribution[i], potentialMapIndexes)) {
                    createRoom(i, position);
                    mappedPlaces[position.x][position.y] = MapState.CREATED;
                }
            // boss room
            } else {
                DiscreteCoordinates position = new DiscreteCoordinates(0, 0);
                for (int x = 0; x < mappedPlaces.length; x++)
                    for (int y = 0; y < mappedPlaces[x].length; y++)
                        if ((mappedPlaces[x][y] == MapState.BOSS_ROOM))
                            position = new DiscreteCoordinates(x, y);
                createRoom(-1, position);
                posBossRoom = position;
            }
        }
    }

    /**
     * Creates a room in the level's map. Can only be implemented by each specific Level.
     * @param type (int): the type of room to create.
     * @param position (DiscreteCoordinates): the position of the room on the map.
     */
    protected abstract void createRoom(int type, DiscreteCoordinates position);

    /**
     * Initializes a map for potential positions, then
     * maps the potential positions for any rooms in the level.
     * @param roomsToPlace (int): the number of rooms to find spots for.
     * @return (MapState[][]): a map of potential places for rooms in the level.
     */
    private MapState[][] mapRooms(int roomsToPlace) {
        MapState[][] roomPlacementMap = new MapState[roomsToPlace][roomsToPlace];

        // initialize map
        for (MapState[] mapStates : roomPlacementMap) Arrays.fill(mapStates, MapState.NULL);
        // set the 'middle' room as PLACED
        roomPlacementMap[roomsList.length / 2][roomsList.length / 2] = MapState.PLACED;
        fillMap(roomPlacementMap, roomsToPlace - 1, false);
        return roomPlacementMap;
    }

    /**
     * Maps the potential positions for any rooms in the level and the boss room.
     * @param roomPlacementMap (MapState[][]): the empty RoomMap.
     * @param roomsToPlace (int): the number of rooms to find spots for.
     * @param isBossRoom (boolean): flag to indicate the boss room, needed for recursive calling.
     */
    private void fillMap(MapState[][] roomPlacementMap, int roomsToPlace, boolean isBossRoom) {
        while (roomsToPlace > 0) {
            DiscreteCoordinates currentPosition = randomPlacedPosition(roomPlacementMap);
            int freeSlots = freeNeighbourSlots(roomPlacementMap, currentPosition.x, currentPosition.y);
            int toPlace;
            if ((roomsToPlace == 1) || (freeSlots == 1)) toPlace = 1;
            else // RandomHelper expects origin and bound to be different
                toPlace = RandomHelper.roomGenerator.nextInt(1, Integer.min(roomsToPlace, freeSlots));
            fillMapAround(roomPlacementMap, currentPosition, toPlace, isBossRoom);
            roomsToPlace -= toPlace;
            roomPlacementMap[currentPosition.x][currentPosition.y] = MapState.EXPLORED;
        }
        // recursive call (with flag protection) for the boss room
        if (!isBossRoom) fillMap(roomPlacementMap, 1, true);
    }

    /**
     * Maps the potential positions for rooms around the current position on the map.
     * @param roomPlacementMap (MapState[][]): the RoomMap.
     * @param currentPosition (DiscreteCoordinates): the current position on the map.
     * @param toPlace (int): the number of rooms to place around the current position.
     * @param isBossRoom (boolean): indicates the boss room.
     */
    private void fillMapAround(MapState[][] roomPlacementMap, DiscreteCoordinates currentPosition, int toPlace, boolean isBossRoom) {
        int placed = 0;
        while (placed < toPlace) {
            int direction = RandomHelper.roomGenerator.nextInt(0, 3);
            switch (direction) {
                // left
                case 0:
                    // checks whether position to the left is on the map and NULL, then writes a room on the map
                    if (currentPosition.x > 0 && roomPlacementMap[currentPosition.x - 1][currentPosition.y] == MapState.NULL) {
                        roomPlacementMap[currentPosition.x - 1][currentPosition.y] = isBossRoom ? MapState.BOSS_ROOM : MapState.PLACED;
                        placed++;
                    }
                    break;
                // right
                case 1:
                    if (currentPosition.x < roomPlacementMap.length - 1 && roomPlacementMap[currentPosition.x + 1][currentPosition.y] == MapState.NULL) {
                        roomPlacementMap[currentPosition.x + 1][currentPosition.y] = isBossRoom ? MapState.BOSS_ROOM : MapState.PLACED;
                        placed++;
                    }
                    break;
                // down
                case 2:
                    if (currentPosition.y > 0 && roomPlacementMap[currentPosition.x][currentPosition.y - 1] == MapState.NULL) {
                        roomPlacementMap[currentPosition.x][currentPosition.y - 1] = isBossRoom ? MapState.BOSS_ROOM : MapState.PLACED;
                        placed++;
                    }
                    break;
                // up
                case 3:
                    if (currentPosition.y < roomPlacementMap.length - 1 && roomPlacementMap[currentPosition.x][currentPosition.y + 1] == MapState.NULL) {
                        roomPlacementMap[currentPosition.x][currentPosition.y + 1] = isBossRoom ? MapState.BOSS_ROOM : MapState.PLACED;
                        placed++;
                    }
                    break;
            }
        }
    }

    /**
     * Returns a pseudorandom position on the map that is PLACED.
     * @param roomPlacementMap (MapState[][]): the filled RoomMap.
     * @return (DiscreteCoordinates): a random position on the map that has been PLACED.
     */
    private DiscreteCoordinates randomPlacedPosition(MapState[][] roomPlacementMap) {
        int x = RandomHelper.roomGenerator.nextInt(0, roomPlacementMap.length - 1);
        int y = RandomHelper.roomGenerator.nextInt(0, roomPlacementMap.length - 1);
        while (roomPlacementMap[x][y] != MapState.PLACED) {
            x = RandomHelper.roomGenerator.nextInt(0, roomPlacementMap.length);
            y = RandomHelper.roomGenerator.nextInt(0, roomPlacementMap.length);
        }
        return new DiscreteCoordinates(x, y);
    }

    /**
     * Returns the number of free slots around a position on the map.
     * @param roomPlacementMap (MapState[][]): the RoomMap.
     * @param x (int): the x coordinate of the position to check.
     * @param y (int): the y coordinate of the position to check.
     * @return (int): the number of free (NULL) slots around the position.
     */
    private int freeNeighbourSlots(MapState[][] roomPlacementMap, int x, int y) {
        int freeSlots = 0;
        if (x > 0 && roomPlacementMap[x - 1][y] == MapState.NULL) {
            freeSlots++;
        }
        if (x < roomsList.length - 1 && roomPlacementMap[x + 1][y] == MapState.NULL) {
            freeSlots++;
        }
        if (y > 0 && roomPlacementMap[x][y - 1] == MapState.NULL) {
            freeSlots++;
        }
        if (y < roomsList.length - 1 && roomPlacementMap[x][y + 1] == MapState.NULL) {
            freeSlots++;
        }
        return freeSlots;
    }

    /**
     * Adds a room to the level at the given position.
     * @param posRoom (DiscreteCoordinates): The position of the room.
     * @param room (Room): The room to add.
     */
    protected void setRoom(DiscreteCoordinates posRoom, ICRogueRoom room) {
        roomsList[posRoom.x][posRoom.y] = room;
    }

    /**
     * Sets up the connectors of a room by checking the surrounding rooms on the map.
     * Has to be implemented by the specific Level.
     * @param roomsMapped (MapState[][]): the filled RoomMap.
     * @param room (ICRogueRoom): the connectors who should be set up.
     */
    protected abstract void setupConnectors(MapState[][] roomsMapped, ICRogueRoom room);

    /**
     * Sets a Connectors destination room.
     * @param posOfRoom (DiscreteCoordinates): the position of the room the connector belongs to.
     * @param destination (String): the name of the destination room.
     * @param connector (ConnectorInRoom): the connector whose destination should be set.
     */
    protected void setRoomConnectorDest(DiscreteCoordinates posOfRoom, String destination, ConnectorInRoom connector) {
        roomsList[posOfRoom.x][posOfRoom.y].getRoomConnectors()[connector.getIndex()].setDestination(destination, connector.getDestCoords());
    }

    /**
     * Locks a connector with a given key id.
     * @param coords (DiscreteCoordinates): the position of the connector's room.
     * @param connector (ConnectorInRoom): the connector.
     * @param keyId (int): the id of the key.
     */
    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector,
                                     int keyId) {
        roomsList[coords.x][coords.y].getRoomConnectors()[connector.getIndex()].lockWithKey(keyId);
    }

    /**
     * Sets the level's starting room by coordinates.
     * @param coords (DiscreteCoordinates): the coordinates of the starting room.
     */
    protected void setStartingRoom(DiscreteCoordinates coords) {
        titleStartRoom = roomsList[coords.x][coords.y].getTitle();
    }

    /**
     * Signals true if the level is completed.
     * @return (boolean): true if the level is completed.
     */
    @Override
    public boolean isOn() {
        return (roomsList[posBossRoom.x][posBossRoom.y] != null) &&
                roomsList[posBossRoom.x][posBossRoom.y].isOn();
    }

    @Override
    public boolean isOff() {
        return !isOn();
    }

    @Override
    public float getIntensity() {
        return isOn() ? 1.0f : 0.0f;
    }

}