package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;


public class Connector extends AreaEntity {

    private State state;
    private String destArea;
    private DiscreteCoordinates destCoords;
    private int keyId;

    private Sprite spriteLocked = new Sprite("icrogue/lockedDoor_"+ getOrientation().ordinal(),
            (getOrientation().ordinal()+1)%2+1, getOrientation().ordinal()%2+1, this);
    private Sprite spriteClosed = new Sprite("icrogue/door_"+ getOrientation().ordinal(),
            (getOrientation().ordinal()+1)%2+1, getOrientation().ordinal()%2+1, this);
    private Sprite spriteInvisible = new Sprite("icrogue/invisibleDoor_"+ getOrientation().ordinal(),
            (getOrientation().ordinal()+1)%2+1, getOrientation().ordinal()%2+1, this);


    public static enum State {
        OPEN, CLOSED, LOCKED, INVISIBLE
    }


    /**
     * Default AreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */

    public Connector(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        state = State.INVISIBLE;
    }

    public void open() {
        if (state == State.CLOSED) state = State.OPEN;
    }

    public void open(ArrayList<Integer> keyIds) {
        if (((state == State.LOCKED) && (keyIds.size() > 0) && keyIds.contains(keyId))) state = State.OPEN;
        else open();
    }

    public void setDestination(String destArea, DiscreteCoordinates destCoords) {
        this.destArea = destArea;
        this.destCoords = destCoords;
        state = State.CLOSED;
    }

    public String getDestArea() {
        return destArea;
    }

    public DiscreteCoordinates getDestCoords() {
        return destCoords;
    }

    public void lockWithKey(int keyId) {
        if (state == State.CLOSED) {
            state = State.LOCKED;
            this.keyId = keyId;
        }
    }

    public void close() {
        if (state == State.OPEN) state = State.CLOSED;
    }

    /**
     * Renders itself on specified canvas.
     *
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        switch (state) {
            case OPEN:
                break;
            case CLOSED:
                spriteClosed.draw(canvas);
                break;
            case LOCKED:
                spriteLocked.draw(canvas);
                break;
            case INVISIBLE:
                spriteInvisible.draw(canvas);
                break;
        }

    }

    /**
     * Get this Interactor's current occupying cells coordinates
     *
     * @return (List of DiscreteCoordinates). May be empty but not null
     */
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates();
        return List.of(coord , coord.jump(new
                Vector((getOrientation().ordinal()+1)%2,
                getOrientation().ordinal()%2)));

    }

    /**
     * Indicate if the current Interactable take the whole cell space or not
     * i.e. only one Interactable which takeCellSpace can be in a cell
     * (how many Interactable which don't takeCellSpace can also be in the same cell)
     *
     * @return (boolean)
     */
    @Override
    public boolean takeCellSpace() {
        return state != State.OPEN;
    }

    /**
     * @return (boolean): true if this is able to have cell interactions
     */
    @Override
    public boolean isCellInteractable() {
        return state == State.OPEN;
    }

    /**
     * @return (boolean): true if this is able to have view interactions
     */
    @Override
    public boolean isViewInteractable() {
        return true;
    }

    /**
     * Call directly the interaction on this if accepted
     *
     * @param v                 (AreaInteractionVisitor) : the visitor
     * @param isCellInteraction
     */
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this,isCellInteraction);
    }
}
