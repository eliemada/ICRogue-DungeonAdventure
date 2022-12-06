package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.actor.Acoustics;
import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;


public class Arrow extends Projectile implements Acoustics {
    private SoundAcoustics fireBallSound;
    private boolean soundHasBeenExecuted = false;


    //Sprite sprite;
    public Arrow(Area room, Orientation orientation, DiscreteCoordinates position) {
        super(room, orientation, position,5,10);
        setSprite(new Sprite("zelda/arrow", 1f, 1f, this ,
                new RegionOfInterest(32 * orientation.ordinal(), 0, 32, 32),
                new Vector(0, 0)));
        fireBallSound = new SoundAcoustics(ResourcePath.getSound("bow_shoot"));
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor areaInteractionVisitor, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) areaInteractionVisitor).interactWith(this , isCellInteraction);
    }
    public void update(float deltatime){
        super.update(deltatime);
    }

    @Override
    public void enterArea(Area area, DiscreteCoordinates position) {
        super.enterArea(area, position);
        fireBallSound.shouldBeStarted();
    }

    /**

    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
 **/

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

}
