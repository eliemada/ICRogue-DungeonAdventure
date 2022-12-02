package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Cherry extends Item{

    public Cherry(Area area, Orientation orientation, DiscreteCoordinates position,Sprite givenSprite) {
        super(area, orientation, position,givenSprite);
    }

    public Cherry(Area area, Orientation orientation, DiscreteCoordinates position,Sprite givenSprite,boolean isCollected) {
        super(area, orientation, position,givenSprite,isCollected);
    }

}
