package ch.epfl.cs107.play.game.icrogue.actor.projectiles;/*
 *	Author:      Leon Petrinos
 *	Date:
 */

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Fire extends Projectile {
    private final static int DEFAULT_DAMAGE_FIRE = 1;
    private final static int DEFAULT_MOVE_DURATION = 5;

    private int damage, duration;
    Sprite spriteFire;
    Sprite myBall;
    private Sprite fireAnimated[] = new Sprite[7];

    /**
     *
     * @Goal Be able to fill a list with all the different fire sprite possible !
     * Currently stuck, because I can not update fire !
     */
    public void fillTheFireList() {
        for (int i = 0; i <= fireAnimated.length-1; i++) {
            fireAnimated[i] = new Sprite("zelda/fire", 1f, 1f, this,
                    new RegionOfInterest(16 * i, 0, 16, 16),
                    new Vector(0f, 0f));
            myBall          = (fireAnimated[i]);

        }
    }

    public Fire(Area room, Orientation orientation, DiscreteCoordinates position) {
        this(room, orientation, position, DEFAULT_DAMAGE_FIRE, DEFAULT_MOVE_DURATION);
    }
    public Fire(Area room, Orientation orientation, DiscreteCoordinates position, int givenDamage,
                int givenDuration) {
        super(room, orientation, position);
        damage = givenDamage;
        duration = givenDuration;
        spriteFire = new Sprite("zelda/fire",1f,1f,this,new RegionOfInterest(0,0,16,16),new Vector(0,0));
    }

    public void draw(Canvas canvas) {
        spriteFire.draw(canvas);
    }

    public void enterArea(Area area){
        area.registerActor(this);
    }

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
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }









}