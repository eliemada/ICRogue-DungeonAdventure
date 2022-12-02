package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class ICRogueActor extends MovableAreaEntity {

    public ICRogueActor(Area room, Orientation orientation,
                        DiscreteCoordinates position){
        super(room, orientation, position);

    }
}
