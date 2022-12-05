package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Enemies extends ICRogueActor {
    private boolean isAlive = true;
    Sprite sprite;
    public Enemies(Area room, Orientation orientation, DiscreteCoordinates position) {
        super(room, orientation, position);
    }
    public void Consume() {
        isAlive = false;
        getOwnerArea().unregisterActor(this);
    }
    public boolean isAlive(){
        return isAlive;
    }
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor areaInteractionVisitor, boolean isCellInteraction) {

    }
}
