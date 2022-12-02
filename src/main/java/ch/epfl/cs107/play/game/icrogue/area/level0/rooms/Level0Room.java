package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0Room extends ICRogueRoom{

    private final String title;
    public Level0Room(DiscreteCoordinates givenRoomCoordinates) {
        super("icrogue/Level0Room",givenRoomCoordinates);
        title = "icrogue/Level0Room"+givenRoomCoordinates.x+givenRoomCoordinates.y;
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
        registerActor(new Background(this,getBehaviorName() ));

    }
}
