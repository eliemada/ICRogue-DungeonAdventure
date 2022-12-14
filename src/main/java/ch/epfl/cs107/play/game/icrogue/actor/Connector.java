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


/**
 * A connector is an AreaEntity that allows the player to switch between rooms.
 * It has the uncanny appearance of a door and can fittingly be opened, closed and locked.
 */
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


    /**
     * The possible states of a connector.
     */
    public enum State {
        OPEN, CLOSED, LOCKED, INVISIBLE
    }


    /**
     * Default AreaEntity constructor augmented with the default state of a connector.
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    public Connector(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        state = State.INVISIBLE;
    }

    /**
     * Opens the connector if it was closed and unlocked.
     */
    public void open() {
        if (state == State.CLOSED) state = State.OPEN;
    }

    /**
     * Tries to open the connector with a given keychain if it is locked,
     * otherwise defers to open().
     * @param keyIds (int[]): The keychain with which to try to unlock.
     */
    public void open(ArrayList<Integer> keyIds) {
        if (((state == State.LOCKED) && (keyIds.size() > 0) && keyIds.contains(keyId))) {
            state = State.OPEN;
        } else open();
    }

    /**
     * Destination area setter, also closes the connector. (Default state is invisible)
     * @param destArea (String): The name of the area to which the connector connects.
     * @param destCoords (DiscreteCoordinates):
     *                   The coordinates of arrival in the destination area.
     */
    public void setDestination(String destArea, DiscreteCoordinates destCoords) {
        this.destArea = destArea;
        this.destCoords = destCoords;
        state = State.CLOSED;
    }

    /**
     * Destination area getter.
     * @return (String): The name of the area to which the connector connects.
     */
    public String getDestArea() {
        return destArea;
    }

    /**
     * Destination coordinates getter.
     * @return (DiscreteCoordinates): The coordinates of arrival in the destination area.
     */
    public DiscreteCoordinates getDestCoords() {
        return destCoords;
    }

    /**
     * Locks the connector with a given keyId if it is not invisible.
     * (This prevents locking a connector that has no destination)
     * @param keyId (int): The id of the key that (un)locks the connector.
     */
    public void lockWithKey(int keyId) {
        if (state != State.INVISIBLE) {
            state = State.LOCKED;
            this.keyId = keyId;
        }
    }

    /**
     * Closes a connector that was open.
     */
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
