package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Fire extends Projectile{

    private final static int DEFAULT_DAMAGE_FIRE = 1;
    private final static int DEFAULT_MOVE_DURATION = 5;

    private int damage, duration;
    Sprite spriteFire;

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


    @Override
    public void enterArea(Area area){
        area.registerActor(this);
    }

    @Override
    public void draw(Canvas canvas) {
        spriteFire.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {

    }
}
