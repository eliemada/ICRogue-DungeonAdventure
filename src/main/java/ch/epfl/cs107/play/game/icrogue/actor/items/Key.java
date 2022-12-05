package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Key extends Item implements Logic {
    private final int ID;

    public Key(Area area, Orientation orientation, DiscreteCoordinates position, int id) {
        super(area, orientation, position);
        setSprite(new Sprite("icrogue/key", 0.6f, 0.6f, this));
        this.ID = id;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this,isCellInteraction);
    }

    public int getId() {
        return ID;
    }

    //IMPORTANT
    // Tried adding logic just under this comment,
    // not sure about what I did,if you could check as you coded the Logic ?

    /**
     * Signal is On if the Key is collected
     * @return a boolean
     */
    @Override
    public boolean isOn() {
        return this.isCollected();
    }

    @Override
    public boolean isOff() {
        return !this.isCollected();
    }


    @Override
    public float getIntensity() {
        if(this.isCollected()) {
            return 0.0f;
        } else {
            return 1.0f;
        }
    }

}
