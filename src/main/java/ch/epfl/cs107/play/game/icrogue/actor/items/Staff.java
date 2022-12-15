package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

/**
 * The staff is a collectible item that can be used to throw fireballs.
 */
public class Staff extends Item{

    private Sprite [] staffSprite = Sprite.extractSprites("zelda/staff",
            8, .5f, .5f,
            this , 32, 32);
    Animation animation = new Animation(3,staffSprite);

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * @Brief Constructor of the Staff, that extends the Class Item
     * @param area
     * @param orientation
     * @param position
     */
    public Staff(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        //setSprite(new Sprite("zelda/staff_water.icon", .5f, .5f, this));

    }

    @Override
    public void update(float deltatime){
        super.update(deltatime);
        animation.update(deltatime);
    }
    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     */
    @Override
    public boolean isViewInteractable() {
        return true;
    }

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     */
    @Override
    public boolean takeCellSpace() {
        return true;
    }

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     */
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this,isCellInteraction);
    }
}
