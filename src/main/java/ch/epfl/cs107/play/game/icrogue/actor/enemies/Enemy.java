package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

// This class defines an abstract Enemy class that extends the ICRogueActor class
public abstract class Enemy extends ICRogueActor {

    // isAlive is a boolean flag that keeps track of whether the enemy is alive or not
    private boolean isAlive;
    // sprite is a Sprite object that can be used to draw the enemy on a Canvas
    Sprite sprite;

    // This constructor takes an Area, an Orientation, and a DiscreteCoordinates object
    // and passes them to the superclass constructor. It also initializes the isAlive flag to true
    public Enemy(Area room, Orientation orientation, DiscreteCoordinates position) {
        super(room, orientation, position);
        isAlive = true;
    }

    // This method sets the isAlive flag to false and unregisters the enemy from the Area it belongs to
    public void Consume() {
        isAlive = false;
        getOwnerArea().unregisterActor(this);
    }

    // This getter method returns the value of the isAlive flag
    public boolean isAlive(){
        return isAlive;
    }

    // This method is required by the ICRogueActor class. It takes a Canvas object and
    // uses the sprite to draw the enemy on the Canvas
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    // This method is also required by the ICRogueActor class. It takes an AreaInteractionVisitor
    // but doesn't have any implementation in this class
    @Override
    public void acceptInteraction(AreaInteractionVisitor areaInteractionVisitor, boolean isCellInteraction) {
    }
}
