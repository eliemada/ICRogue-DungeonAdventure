package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ICRoguePlayer extends ICRogueActor implements Interactor {

    private static final int ANIMATION_DURATION = 5;
    private final Keyboard keyboard;

    private boolean hasStaff = false;
    private ArrayList<Integer> keyIds = new ArrayList<>();
    private static final int MOVE_DURATION = 5;
    private boolean isBetweenRooms = false;
    private String destArea;
    private Sprite [][] sprites = Sprite.extractSprites("zelda/player",
            4, 1, 2,
            this , 16, 32, new Orientation [] { Orientation .DOWN ,
                    Orientation .RIGHT , Orientation .UP , Orientation . LEFT });
    // crée un tableau de 4 animation
    private Animation[] animations =
            Animation.createAnimations( ANIMATION_DURATION /2, sprites );
    private Fire fireball;
    private ICRogueInteractionHandler handler;
    public ICRoguePlayer(Area room, Orientation orientation, DiscreteCoordinates position
                         ) {
        super(room, orientation, position);
        keyboard = getOwnerArea().getKeyboard();
        handler = new ICRoguePlayerInteractionHandler();
    }

    public boolean isBetweenRooms() {
        return isBetweenRooms;
    }

    public String getDestArea() {
        return destArea;
    }

    @Override
    public void enterArea(Area area, DiscreteCoordinates position){
        super.enterArea(area, position);
        isBetweenRooms = false;
        //Put the boolean here
        area.setIsInRoom(true);
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT),deltaTime);
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP),deltaTime);
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT),deltaTime);
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN),deltaTime);
        if(keyboard.get(Keyboard.X).isPressed() && hasStaff){
            fireball = new Fire(getOwnerArea(), getOrientation(),
                    getCurrentMainCellCoordinates());
            fireball.enterArea(getOwnerArea(),getCurrentMainCellCoordinates());
        }
    }

    private void ifKeyIsPressed(Button pressedKey){

    }
    private void moveIfPressed(Orientation orientation, Button pressedKey,float deltatime){
        if(pressedKey.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
                animations[getOrientation().ordinal()].update(deltatime);
            }

        }
    }

    @Override
    public void draw(Canvas canvas) {
        switch (getOrientation()) {
            case UP -> animations[Orientation.UP.ordinal()].draw(canvas);
            case DOWN -> animations[Orientation.DOWN.ordinal()].draw(canvas);
            case LEFT ->  animations[Orientation.LEFT.ordinal()].draw(canvas);
            case RIGHT -> animations[Orientation.RIGHT.ordinal()].draw(canvas);
        }
    }



    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return keyboard.get(keyboard.W).isPressed();
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler,isCellInteraction);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true ;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    private class ICRoguePlayerInteractionHandler implements ICRogueInteractionHandler{
        public void interactWith(Cherry cherry,boolean isCellInteraction){
            if (cherry.isCellInteractable()){
                acceptInteraction(this,isCellInteraction);
            }
            if (wantsCellInteraction()){
                cherry.collect();
            }
        }

        public void interactWith(Staff staff, boolean isCellInteraction){
            if (staff.isCellInteractable()){
                acceptInteraction(this,isCellInteraction);
            }
            if (wantsViewInteraction()){
                staff.collect();
                hasStaff = true;
            }
        }

        public void interactWith(Key key, boolean isCellInteraction){
            if (key.isCellInteractable()){
                acceptInteraction(this,isCellInteraction);
            }
            if (wantsCellInteraction()){
                key.collect();
                keyIds.add(key.getId());
            }
        }

        // If the player is interacting remotely, it tries to unlock the connector (the connector
        // changes from locked to open if the player is in possession of the associated key)
        // If it is in touch/contact interaction and not moving
        // (!isDisplacementOccurs()), it can transit to the destination of the connectors.
        public void interactWith(Connector connector, boolean isCellInteraction){
            if (connector.isCellInteractable()) acceptInteraction(this, isCellInteraction);
            if (wantsViewInteraction()){
                connector.openWithKey(keyIds);
            } else if (wantsCellInteraction() && !isDisplacementOccurs()){
                isBetweenRooms = true;
                destArea = connector.getDestArea();
                leaveArea();
            }

        }
    }


}
