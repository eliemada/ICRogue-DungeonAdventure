package ch.epfl.cs107.play.game.icrogue.actor.projectiles;
import ch.epfl.cs107.play.game.actor.Acoustics;
import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Fire extends Projectile implements Acoustics {
    private final static int DEFAULT_MOVE_DURATION = 5;
    private static final int ANIMATION_DURATION    = 5;
    private final static int            DEFAULT_DAMAGE_FIRE = 1;

    private              SoundAcoustics fireBallSound;
    private ICRogueFireInteractionHandler handler;


    private boolean soundHasBeenExecuted = false;
    private int damage, duration;
    private Sprite [] sprites = Sprite.extractSprites("zelda/fire",
            7, 1, 1,
            this , 16, 16);
    Animation animation = new Animation(3,sprites);

    //Sprite spriteFire;



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
        //spriteFire = new Sprite("zelda/fire",1f,1f,this,new RegionOfInterest(0,0,16,16),new Vector(0,0));
        fireBallSound = new SoundAcoustics(ResourcePath.getSound("fireBall"),0.5f,false,false,false,false);
        fireBallSound.shouldBeStarted();
        handler = new ICRogueFireInteractionHandler();
    }
    public Fire(Area room, Orientation orientation, DiscreteCoordinates position) {
        this(room, orientation, position, DEFAULT_DAMAGE_FIRE, DEFAULT_MOVE_DURATION);
    }

    @Override
    public void update(float deltatime){
        super.update(deltatime);
        animation.update(deltatime);
    }

    /**
     * @author Elie BRUNO (elie.bruno@epfl.ch)
     * Draw methode that allaws to draw the fireball on the canvas
     * @param canvas target, not null
     */
    public void draw(Canvas canvas) {
        animation.draw(canvas);
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
    @Override
    public void bip(Audio audio) {
        if (!soundHasBeenExecuted){
            fireBallSound.bip(audio);
            soundHasBeenExecuted = true;}
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction((ICRogueFireInteractionHandler)handler, isCellInteraction);
    }

    private class ICRogueFireInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(Arrow arrow, boolean isCellInteraction) {
            if (wantsViewInteraction()) {
                arrow.consume();
            }
        }

        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            if (cell.is(ICRogueBehavior.ICRogueCellType.WALL) || cell.is(ICRogueBehavior.ICRogueCellType.HOLE)) {
                acceptInteraction(this, isCellInteraction);
                consume();
            }

        }
    }









    }