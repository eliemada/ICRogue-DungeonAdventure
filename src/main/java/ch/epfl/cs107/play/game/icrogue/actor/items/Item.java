package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

/**
 * A base class for items in a game. It extends CollectableAreaEntity, which means that it is an
 * entity that can be picked up by the player in the game.
 *
 * @author Elie BRUNO (elie.bruno@epfl.ch)
 * @author Sekuba
 */
public abstract class Item extends CollectableAreaEntity {

    /** Indicates whether the item has been picked up or not. */
    private Boolean isPickedUp = false;

    /** The item's visual appearance as a sprite. */
    private Sprite sprite;

    /**
     * Constructs a new item.
     * @param area the area in which the item is located
     * @param orientation the orientation of the item
     * @param position the position of the item
     */
    public Item(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    /**

     Sets the sprite of the item.
     @param givenSprite the sprite to be set
     */
    protected void setSprite(Sprite givenSprite){
        sprite = givenSprite;
    }
    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * @param canvas target, not null
     * Draws the item on the screen if it has not been picked up yet.
     * @param canvas the canvas on which to draw the item, not null
     */
    @Override
    public void draw(Canvas canvas){
        if (!isCollected()){
            sprite.draw(canvas);
        }
    }


    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    /**
     * Method that needs to be implemented to define the interactions
     * @param v (AreaInteractionVisitor) : the visitor
     * @param isCellInteraction
     */

    @Override
    public abstract void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction);
}
