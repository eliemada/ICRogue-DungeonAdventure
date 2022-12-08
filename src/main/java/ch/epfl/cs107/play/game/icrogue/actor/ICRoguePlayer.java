package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Projectile;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ICRoguePlayer extends ICRogueActor implements Interactor {

    private static final int ANIMATION_DURATION = 2;
    private final Keyboard keyboard;
    private     float        healthPoints;
    private TextGraphics shownHp;

    private Sprite[][] copain;
    private boolean hasStaff = false;
    private ArrayList<Integer> keychain = new ArrayList<>();
    private static final int MOVE_DURATION = 5;
    private Connector insideConnector = null;
    private Sprite [][] sprites;
    // crée un tableau de 4 animation
    private Animation[] animationsCopain;
    private Animation[] animationsMainPlayer;
    private Fire        fireball;
    private Sprite shadow;
    private ICRogueInteractionHandler handler;
    public ICRoguePlayer(Area room, Orientation orientation, DiscreteCoordinates position
                         ) {
        super(room, orientation, position);
        healthPoints = 10;
        shownHp = new TextGraphics(Integer.toString((int)healthPoints), 0.6f, Color.RED);
        shownHp.setParent(this);
        shownHp.setAnchor(new Vector(0.3f, 2.1f));
        keyboard = getOwnerArea().getKeyboard();
        handler = new ICRoguePlayerInteractionHandler();
        copain = Sprite.extractSpritesCol("boy.1",4, 0.5f, 0.65625f, this,16,21,new Vector(0,-0.33f),
                new Orientation [] { Orientation .DOWN ,
                Orientation .LEFT , Orientation .UP , Orientation . RIGHT });
        sprites = Sprite.extractSprites("zelda/player",
                4, 1, 2,
                this , 16, 32, new Orientation [] { Orientation .DOWN ,
                        Orientation .RIGHT , Orientation .UP , Orientation . LEFT });
        animationsMainPlayer =
        Animation.createAnimations( ANIMATION_DURATION /2, sprites );
        animationsCopain = Animation.createAnimations( ANIMATION_DURATION /2, copain );
        shadow = new Sprite("shadow",1,1,this,new RegionOfInterest(0,0,16,16),new Vector(0,-0.1f));
    }

    public boolean isAlive(){
        return !(healthPoints <= 0);
    }
    public Connector getInsideConnector() {
        return insideConnector;
    }

    @Override
    public void enterArea(Area area, DiscreteCoordinates position){
        super.enterArea(area, position);
        area.visit();
        insideConnector = null;
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        if (isAlive()) {
            shownHp.setText(Integer.toString((int)healthPoints));
        }

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

    private void touchedBy(Projectile projectile){
        healthPoints -= projectile.getDamage();
    }

    private void moveIfPressed(Orientation orientation, Button pressedKey,float deltatime){
        if(pressedKey.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
                animationsMainPlayer[getOrientation().ordinal()].update(deltatime);
                animationsCopain[getOrientation().ordinal()].update(deltatime);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        shadow.draw(canvas);
        switch (getOrientation()) {
            case UP -> {
                animationsMainPlayer[Orientation.UP.ordinal()].draw(canvas);
                animationsCopain[Orientation.UP.ordinal()].draw(canvas);
            }
            case DOWN -> {
                animationsMainPlayer[Orientation.DOWN.ordinal()].draw(canvas);
                animationsCopain[Orientation.DOWN.ordinal()].draw(canvas);
            }
            case LEFT -> {
                animationsMainPlayer[Orientation.LEFT.ordinal()].draw(canvas);
                animationsCopain[Orientation.LEFT.ordinal()].draw(canvas);
            }
            case RIGHT -> {
                animationsMainPlayer[Orientation.RIGHT.ordinal()].draw(canvas);
                animationsCopain[Orientation.RIGHT.ordinal()].draw(canvas);
            }
        }
        shownHp.draw(canvas);
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
            if (wantsCellInteraction()) cherry.collect();
        }

        public void interactWith(Staff staff, boolean isCellInteraction){
            if (wantsViewInteraction()){
                staff.collect();
                hasStaff = true;
            }
        }

        public void interactWith(Key key, boolean isCellInteraction){
            if (wantsCellInteraction()){
                key.collect();
                keychain.add(key.getId());
            }
        }

        // If the player is interacting remotely, it tries to unlock the connector (the connector
        // changes from locked to open if the player is in possession of the associated key)
        // If it is in touch/contact interaction and not moving
        // (!isDisplacementOccurs()), it can transit to the destination of the connectors.
        public void interactWith(Connector connector, boolean isCellInteraction){
            // if (connector.isCellInteractable()) acceptInteraction(this, isCellInteraction);
            if (wantsViewInteraction()){
                connector.open(keychain);
            }
            if (wantsCellInteraction() && !isDisplacementOccurs()){
                insideConnector = connector;
                leaveArea();
            }

        }

        /**
         * @Brief Small method to allow the player to interact with the arrow, It allows us to only receive
         * damage once from the arrow because if you don't check that the arrow is not consumed, you could
         * try to make the arrow unregister multiple times which would lead to multiple errors and only
         * take damage once from the arrow.
         * @param arrow
         * @param isCellInteraction
         */
        public void interactWith(Arrow arrow, boolean isCellInteraction){

            if(isCellInteraction){
                touchedBy(arrow);
                arrow.consume();
            }
        }
    }


}
