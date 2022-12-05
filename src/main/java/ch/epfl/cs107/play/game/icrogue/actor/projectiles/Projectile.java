package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public abstract class Projectile extends ICRogueActor implements Consumable, Interactor
{
    private ICRogueProjectileInteractionHandler handler;

    private final static int DEFAULT_MOVE_DURATION = 10;
    private final static int DEFAULT_DAMAGE = 1;
    //private final static boolean ISCONSUMED = false;
    private int moveDuration,damage;
    private boolean isConsumed;
    private Sprite sprite;


    public Projectile(Area room, Orientation orientation, DiscreteCoordinates position) {
        this(room, orientation, position,DEFAULT_DAMAGE,DEFAULT_MOVE_DURATION);
    }
    public Projectile(Area room, Orientation orientation, DiscreteCoordinates position, int givenDamage,
                      int givenMoveduration) {
        super(room, orientation, position);
        damage = givenDamage;
        moveDuration = givenMoveduration;
        isConsumed = false;
        handler = new ICRogueProjectileInteractionHandler();

    }


    @Override
    public void update(float deltatime){
        super.update(deltatime);
        move(moveDuration);
    }

    public void consume(){
        getOwnerArea().unregisterActor(this);
        isConsumed = true;
    }

    public boolean isConsumed(){
        return isConsumed;
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
        return !isConsumed;
    }

    @Override
    public boolean wantsViewInteraction() {
        return !isConsumed;
    }


    @Override
    public boolean takeCellSpace(){
        return false;
    }

    @Override
    public boolean isCellInteractable(){
        return true;
    }
    @Override
    public boolean isViewInteractable(){
        return true;
    }
    @Override
    public abstract void acceptInteraction(AreaInteractionVisitor areaInteractionVisitor,
                                  boolean isCellInteraction);

    public void draw(Canvas canvas){
        sprite.draw(canvas);
    };

    protected void setSprite(Sprite givenSprite){
        sprite = givenSprite;
    }


    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction((ICRogueProjectileInteractionHandler)handler, isCellInteraction);
    }

    private class ICRogueProjectileInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            if (cell.is(ICRogueBehavior.ICRogueCellType.WALL) || cell.is(ICRogueBehavior.ICRogueCellType.HOLE)) {
                acceptInteraction(this, isCellInteraction);
                consume();
            }

        }


    }

}
