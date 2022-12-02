package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0Room extends ICRogueRoom{
    public Level0Room(DiscreteCoordinates givenRoomCoordinates) {
        super("icrogue/Level0Room",givenRoomCoordinates);
    }


    @Override
    public String getTitle() {
        return null;
    }

    @Override
    protected void createLevel() {
        registerActor(new Background(this,getBehaviorName() ));

    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return null;
    }
}
