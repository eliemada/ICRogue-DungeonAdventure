package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Level implements Logic {
    // TODO static or dynamic array?
    private ICRogueRoom[][] roomsList;

    private DiscreteCoordinates posArrival;
    private DiscreteCoordinates posBossRoom = new DiscreteCoordinates(0, 0);
    private String titleStartRoom;

    // TODO is this startPosition or startRoom
    protected Level (DiscreteCoordinates startPosition, int width , int height) {
        generateFixedMap(width, height);
    }

    protected Level () {}

    protected void generateRandomMap(int[] roomsDistribution) {
        int nbRooms = Arrays.stream(roomsDistribution).sum();
        generateFixedMap(nbRooms, nbRooms);
        MapState[][] mappedRooms = generateRandomRoomPlacement(roomsDistribution, nbRooms);
        generateRooms(roomsDistribution, mappedRooms);
        for (ICRogueRoom[] rooms : roomsList)
            for (ICRogueRoom room : rooms)
                if (room != null)
                    setupConnectors(mappedRooms, room);

    }

    protected abstract void setupConnectors(MapState[][] roomsMapped, ICRogueRoom room);

    private void generateRooms(int[] roomsDistribution, MapState[][] mappedRooms) {
        for (int i = 0; i < roomsDistribution.length; i++) {
            ArrayList<DiscreteCoordinates> potentialMapIndexes = new ArrayList<>();
            // index all rooms that are PLACED or EXPLORED
            for (int x = 0; x < mappedRooms.length; x++)
                for (int y = 0; y < mappedRooms[x].length; y++)
                    if ((mappedRooms[x][y] == MapState.PLACED) || (mappedRooms[x][y] == MapState.EXPLORED))
                        potentialMapIndexes.add(new DiscreteCoordinates(x, y));
            for (DiscreteCoordinates position : RandomHelper.chooseKInList(roomsDistribution[i], potentialMapIndexes)) {
                createRoom(i, position);
                mappedRooms[position.x][position.y] = MapState.CREATED;
            }
        }
    }

    protected abstract void createRoom(int type, DiscreteCoordinates position);

    protected MapState [][] generateRandomRoomPlacement(int[] roomsDistribution, int rommsToPlace) {
        MapState[][] roomPlacementMap = new MapState[rommsToPlace][rommsToPlace];

        // initialize map
        for (int i = 0; i < roomPlacementMap.length; i++) {
            for (int j = 0; j < roomPlacementMap[i].length; j++) {
                roomPlacementMap[i][j] = MapState.NULL;
            }
        }
        // set the 'middle' room as PLACED
        roomPlacementMap[roomsList.length / 2][roomsList.length / 2] = MapState.PLACED;
        mapRooms(roomPlacementMap, rommsToPlace, false);
        return roomPlacementMap;
    }

    private void mapRooms(MapState[][] roomPlacementMap, int roomsToPlace, boolean isBossRoom) {
        while (roomsToPlace > 0) {
            DiscreteCoordinates currentPosition = randomPlacedCoords(roomPlacementMap);
            int freeSlots = calcFreeSlots(roomPlacementMap, currentPosition.x, currentPosition.y);
            int toPlace;
            if (roomsToPlace == 1) toPlace = 1;
            else
                toPlace = RandomHelper.roomGenerator.nextInt(1 , Integer.min(roomsToPlace, freeSlots));
            mapRoomsAround(roomPlacementMap, currentPosition, toPlace, isBossRoom);
            roomsToPlace -= toPlace;
            roomPlacementMap[currentPosition.x][currentPosition.y] = MapState.EXPLORED;
        }
        if(!isBossRoom) mapRooms(roomPlacementMap, roomsToPlace, true);
    }

    private void mapRoomsAround(MapState[][] roomPlacementMap, DiscreteCoordinates currentPosition, int toPlace, boolean isBossRoom) {
        int placed = 0;
        while (placed < toPlace) {
            int direction = RandomHelper.roomGenerator.nextInt(0, 3);
            switch (direction) {
                // TODO integrate left, right etc. into the MapState enum?
                // left
                case 0:
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

    private DiscreteCoordinates randomPlacedCoords(MapState[][] roomPlacementMap) {
        int x = RandomHelper.roomGenerator.nextInt(0, roomPlacementMap.length-1);
        int y = RandomHelper.roomGenerator.nextInt(0, roomPlacementMap.length-1);
        while (roomPlacementMap[x][y] != MapState.PLACED) {
            x = RandomHelper.roomGenerator.nextInt(0, roomPlacementMap.length);
            y = RandomHelper.roomGenerator.nextInt(0, roomPlacementMap.length);
        }
        return new DiscreteCoordinates(x, y);
    }

    private int calcFreeSlots(MapState[][] roomPlacementMap, int x, int y) {
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

    private void printMap(MapState [][] map) {
        System.out.println("Generated map:");
        System.out.print(" | ");
        for (int j = 0; j < map[0]. length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        System.out.print("--|-");
        for (int j = 0; j < map[0]. length; j++) {
            System.out.print("--");
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
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
        for (ICRogueRoom[] rooms: roomsList) {
            for (ICRogueRoom room: rooms) {
                if (room != null) parent.addArea(room);
            }
        }
    }

    protected void generateFixedMap(int width,int height) {
        roomsList = new ICRogueRoom[width][height];
    }

    public String getTitleStartRoom() {
        return titleStartRoom;
    }

    protected void setRoom(DiscreteCoordinates posRoom, ICRogueRoom room){
        roomsList[posRoom.x][posRoom.y] = room;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates posOfRoom, String destination, ConnectorInRoom connector){
        roomsList[posOfRoom.x][posOfRoom.y].getRoomConnectors()[connector.getIndex()].setDestination(destination, posArrival);
    }

    protected void setRoomConnector(DiscreteCoordinates posOfRoom, String destination, ConnectorInRoom connector){
        setRoomConnectorDestination(posOfRoom, destination, connector);
        roomsList[posOfRoom.x][posOfRoom.y].getRoomConnectors()[connector.getIndex()].close();

    }

    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector,
                                     int keyId){
        roomsList[coords.x][coords.y].getRoomConnectors()[connector.getIndex()].lockWithKey(keyId);
    }

    protected void setStartingRoom(DiscreteCoordinates coords){
        titleStartRoom = roomsList[coords.x][coords.y].getTitle();
    }

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
    public float getIntensity(){
        return isOn() ? 1.0f : 0.0f;
    }

}