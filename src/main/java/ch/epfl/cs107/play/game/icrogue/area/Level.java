package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.Arrays;

public abstract class Level implements Logic {
    // TODO static or dynamic array?
    private ICRogueRoom roomsMapped[][];

    private DiscreteCoordinates posArrival;
    private DiscreteCoordinates posBossRoom = new DiscreteCoordinates(0, 0);
    private String titleStartRoom;

    // TODO is this startPosition or startRoom
    protected Level (DiscreteCoordinates startPosition, int width , int height) {
        generateFixedMap(width, height);
    }

    protected Level (DiscreteCoordinates startPosition, int[] roomsDistribution) {
        generateRandomMap(roomsDistribution);
    }

    private void generateRandomMap(int[] roomsDistribution) {
        int dimension = Arrays.stream(roomsDistribution).sum();
        generateFixedMap(dimension, dimension);
    }

    protected MapState [][] generateRandomRoomPlacement() {
        MapState[][] roomPlacementMap = new MapState[roomsMapped.length][roomsMapped.length];
        for (MapState[] row : roomPlacementMap) {
            for (MapState cell : row) {
                cell = MapState.NULL;
            }
        }
        return roomPlacementMap;
    }

    protected enum MapState {

        NULL , // Empty space
        PLACED , // The room has been placed but not yet explored by the room placement algorithm
        EXPLORED , // The room has been placed and explored by the algorithm
        BOSS_ROOM , // The room is a boss room
        CREATED; // The room has been instantiated in the room map

        @Override
        public String toString() {
            return Integer.toString(ordinal());
        }
    }

    public void registerRooms(AreaGame parent){
        for (ICRogueRoom[] rooms: roomsMapped) {
            for (ICRogueRoom room: rooms) {
                if (room != null) parent.addArea(room);
            }
        }
    }

    protected void generateFixedMap(int width,int height) {
        roomsMapped = new ICRogueRoom[width][height];
    }

    public String getTitleStartRoom() {
        return titleStartRoom;
    }

    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom room){
        roomsMapped[coords.x][coords.y] = room;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination,
                                               ConnectorInRoom connector){
        roomsMapped[coords.x][coords.y].getRoomConnectors()[connector.getIndex()].setDestination(destination, posArrival);
    }

    protected void setRoomConnector(DiscreteCoordinates coords, String destination,
                                    ConnectorInRoom connector){
        setRoomConnectorDestination(coords, destination, connector);
        roomsMapped[coords.x][coords.y].getRoomConnectors()[connector.getIndex()].close();

    }

    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector,
                                     int keyId){
        roomsMapped[coords.x][coords.y].getRoomConnectors()[connector.getIndex()].lockWithKey(keyId);
    }

    protected void setStartingRoom(DiscreteCoordinates coords){
        titleStartRoom = roomsMapped[coords.x][coords.y].getTitle();
    }

    @Override
    public boolean isOn() {
        return (roomsMapped[posBossRoom.x][posBossRoom.y] != null) &&
                roomsMapped[posBossRoom.x][posBossRoom.y].isOn();
    }

    @Override
    public boolean isOff() {
        return !isOn();
    }

    @Override
    public float getIntensity(){
        return isOn() ? 1.0f : 0.0f;
    }

}