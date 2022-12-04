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
 * @author Elie BRUNO (elie.bruno@epfl.ch)
 * @author Sekuba
 */
public abstract class Item extends CollectableAreaEntity {
    private Sprite sprite;

    public Item(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    protected void setSprite(Sprite givenSprite){
        sprite = givenSprite;
    }

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * @param canvas target, not null
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

    @Override
    public abstract void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction);
}
