

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

/**
 * Arrow class represents an arrow that can be fired from a bow
 */
/**
 * Arrow class represents an arrow that can be fired from a bow
 */
public class Arrow extends Projectile implements Acoustics {
    // SoundAcoustics object for the sound effect of the arrow being fired
    private SoundAcoustics arrowSound;
    private SoundAcoustics damageSound;
    private Orientation    orientation;

    private boolean soundHasBeenExecuted = false;

    /**
     * Constructor for the Arrow class
     *
     * @param room        the area in which the arrow will exist
     * @param orientation the orientation of the arrow
     * @param position    the position of the arrow
     */
    public Arrow(Area room, Orientation orientation, DiscreteCoordinates position) {
        // Call the superclass constructor with the given parameters and the arrow's speed and damage
        super(room, orientation, position, 5, 10);
        // Store the orientation of the arrow
        this.orientation = orientation;
        // Initialize the arrowSound object with the sound effect and its parameters
        arrowSound = new SoundAcoustics(ResourcePath.getSound("bow_shoot"), 0.5f, false, false, false,
                false);
        damageSound = new SoundAcoustics(ResourcePath.getSound("damage"), 0.5f, false, false, false,
                false);
    }

    /**
     * Handle the interaction between the arrow and other objects in the game
     *
     * @param areaInteractionVisitor the object with which the arrow is interacting
     * @param isCellInteraction      boolean indicating whether the interaction is happening on a cell or not
     */
    @Override
    public void acceptInteraction(AreaInteractionVisitor areaInteractionVisitor, boolean isCellInteraction) {
        // If the arrow hasn't been consumed, handle the interaction with the ICRogueInteractionHandler
        if (!isConsumed())
            ((ICRogueInteractionHandler) areaInteractionVisitor).interactWith(this, isCellInteraction);
    }

    /**
     * Update the arrow's state
     *
     * @param deltaTime the time elapsed since the previous update
     */
    @Override
    public void update(float deltaTime) {
        // Update the arrow's state
        super.update(deltaTime);
    }

    /**
     * Called when the arrow enters a new area
     *
     * @param area     the new area
     * @param position the position of the arrow in the new area
     */
    @Override
    public void enterArea(Area area, DiscreteCoordinates position) {
        // Call the superclass method to enter the given area at the given position
        super.enterArea(area, position);
        // Set the arrow's sprite using the given orientation and position
        setSprite(new Sprite("zelda/arrow", 1f, 1f, this,
                new RegionOfInterest(32 * orientation.ordinal(), 0, 32, 32),
                new Vector(0, 0)));
        // Tell the fireBallSound object that the sound should be played
        arrowSound.shouldBeStarted();
    }

    public void setDamageSound(){
        damageSound.shouldBeStarted();
    }
    /**
     * Check if the arrow is interactable
     *
     * @return true if the arrow is visible in the game world, false otherwise
     */
    @Override
    public boolean isViewInteractable() {
        // The arrow is interactable if it is visible in the game world
        return true;
    }

    /**
     * Play the arrow's sound effect
     *
     * @param audio the Audio object to be used to play the sound
     */
    public void bip(Audio audio) {
        // If the sound hasn't been played yet, play it and set soundHasBeenExecuted to true
        if (!soundHasBeenExecuted) {
            arrowSound.bip(audio);
            soundHasBeenExecuted = true;
        }
        damageSound.bip(audio);
    }
}
