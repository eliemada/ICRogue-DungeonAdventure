package ch.epfl.cs107.play.game.icrogue.actor.projectiles;/*
 *	Author:      Leon Petrinos
 *	Date:
 */

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
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
    private static final int ANIMATION_DURATION    = 5;

    private int damage, duration;
    Sprite spriteFire;
    //Sprite myBall;
    //private Sprite fireAnimated[] = new Sprite[7];
    // crée un tableau de 4 animation

    /**
     *
     * @Goal Be able to fill a list with all the different fire sprite possible !
     * Currently stuck, because I can not update fire !
     */
    /**
    public void fillTheFireList() {
        for (int i = 0; i <= fireAnimated.length-1; i++) {
            fireAnimated[i] = new Sprite("zelda/fire", 1f, 1f, this,
                    new RegionOfInterest(16 * i, 0, 16, 16),
                    new Vector(0f, 0f));
            myBall          = (fireAnimated[i]);

        }
    }*/


    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * @Brief Fire Constructor that initializes the fireball, with the following parameters :
     * @param room : current Area
     * @param orientation : orientation of the player
     * @param position : position of the spawn of the fireball
     * @param givenDamage : if not given then, default value
     * @param givenDuration : : if not given then, default value
     */
    public Fire(Area room, Orientation orientation, DiscreteCoordinates position, int givenDamage,
                int givenDuration) {
        super(room, orientation, position);
        damage = givenDamage;
        duration = givenDuration;
        spriteFire = new Sprite("zelda/fire",1f,1f,this,new RegionOfInterest(0,0,16,16),new Vector(0,0));
    }
    public Fire(Area room, Orientation orientation, DiscreteCoordinates position) {
        this(room, orientation, position, DEFAULT_DAMAGE_FIRE, DEFAULT_MOVE_DURATION);
    }

    @Override
    public void update(float deltatime){
        super.update(deltatime);
    }

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * Draw methode that allaws to draw the fireball on the canvas
     * @param canvas target, not null
     */
    public void draw(Canvas canvas) {
        spriteFire.draw(canvas);
    }


    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * @Brief Method to register the fireball on the area.
     * @param area
     */


    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * @return the current cells
     */
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    /**
     * @Brief Boolean to check if the Projectile take the cell space, in this case no,
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * @return
     */
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     */
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * @Brief Method to start accepting Interactions
     * @param v (AreaInteractionVisitor) : the visitor
     * @param isCellInteraction
     */
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     */
    @Override
    public boolean isViewInteractable() {
        return true;
    }









}