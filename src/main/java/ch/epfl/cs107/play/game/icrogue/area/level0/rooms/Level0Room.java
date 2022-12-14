package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import java.util.ArrayList;

public class Level0Room extends ICRogueRoom{

    private final String title;

    public enum Level0Connectors implements ConnectorInRoom {
        // ordre des attributs: position , destination , orientation
        W(new DiscreteCoordinates(0, 4),
                new DiscreteCoordinates(8, 5), Orientation.LEFT),
        S(new DiscreteCoordinates(4, 0),
                new DiscreteCoordinates(5, 8), Orientation.DOWN),
        E(new DiscreteCoordinates(9, 4),
                new DiscreteCoordinates(1, 5), Orientation.RIGHT),
        N(new DiscreteCoordinates(4, 9),
                new DiscreteCoordinates(5, 1), Orientation.UP);

        private final DiscreteCoordinates position;
        private final DiscreteCoordinates destination;
        private final Orientation orientation;

        Level0Connectors(DiscreteCoordinates position, DiscreteCoordinates destination, Orientation orientation) {
            this.position = position;
            this.destination = destination;
            this.orientation = orientation;
        }

        public Orientation getOrientation(){
            return orientation;
        }
        @Override
        public DiscreteCoordinates getDestCoords(){
            return destination;
        }

        private static ArrayList<Orientation> getAllConnectorsOrientation() {
            ArrayList <Orientation > orientations = new ArrayList < > ();
            for (Level0Connectors connector: Level0Connectors.values()) {
                orientations.add(connector.orientation);
            }
            return orientations;
        }

        private static ArrayList<DiscreteCoordinates> getAllConnectorsPosition() {
            ArrayList<DiscreteCoordinates> positions = new ArrayList<>();
            for (Level0Connectors connector: Level0Connectors.values()) {
                positions.add(connector.position);
            }
            return positions;
        }

        @Override
        public int getIndex() {
            return this.ordinal();
        }

        // TODO implement destinations from this
        @Override
        public DiscreteCoordinates getDestination() {
            return destination;
        }
    }

    public Level0Room(DiscreteCoordinates givenRoomCoordinates) {
        super(Level0Connectors.getAllConnectorsPosition(), Level0Connectors.getAllConnectorsOrientation(),"icrogue/Level0Room",givenRoomCoordinates);
        title = "icrogue/level0"+givenRoomCoordinates.x+givenRoomCoordinates.y;
    }

    public final float getCameraScaleFactor() {
        return 11f;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    protected void createArea() {
        super.createArea();
        registerActor(new Background(this,getBehaviorName() ));
    }
}
