package ch.epfl.cs107.play.game.icrogue.area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public abstract class Level {
    // TODO static or dynamic array?
    private ICRogueRoom roomsMapped[][];

    private DiscreteCoordinates posArrival;
    private DiscreteCoordinates posBossRoom;
    private String titleStartRoom;

    public Level(DiscreteCoordinates posArrival, DiscreteCoordinates dimensionsMap) {
        this.posArrival = posArrival;
        this.posBossRoom = new DiscreteCoordinates(0, 0);
    }

    public void registerRooms(AreaGame parent){
        for (ICRogueRoom[] rooms: roomsMapped) {
            for (ICRogueRoom room: rooms) {
                if (room != null) parent.addArea(room);
            }
        }
    }

    protected void generateFixedMap(DiscreteCoordinates dimensionsMap){
        roomsMapped = new ICRogueRoom[dimensionsMap.x][dimensionsMap.y];
    }

    public String getTitleStartRoom() {
        return titleStartRoom;
    }

    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom room){
        roomsMapped[coords.x][coords.y] = room;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination,
                                               ConnectorInRoom connector){
        roomsMapped[coords.x][coords.y].getRoomConnectors()[connector.getIndex()].setDestArea(destination);
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
}