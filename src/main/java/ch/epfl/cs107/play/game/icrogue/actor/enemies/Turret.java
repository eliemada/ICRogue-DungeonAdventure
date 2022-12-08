package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Turret extends Enemy implements Interactor {
    // Constant defining how long the Turret should wait before firing another arrow
    private static final int COOLDOWN = 2;

    // Handler for interactions with the Turret
    private final ICRogueTurretInteractionHandler handler;

    // Array of orientations specifying the directions in which the Turret should fire arrows
    private final Orientation[] arrowOrientations;

    // Sprite used to draw the Turret on the screen
    private final Sprite sprite;

    // Counter used to track how long the Turret should wait before firing another arrow
    private float cooldownCounter = COOLDOWN;
    // Reference to the Canvas object
    private Canvas canvas;

    public Turret(Area room, Orientation orientation, DiscreteCoordinates position,
                  Orientation...arrowOrientations) {
        super(room, orientation, position);
        // Create a new sprite for the Turret
        sprite = new Sprite("icrogue/static_npc", 1f, 1f, this);
        // Create a new interaction handler for the Turret
        handler = new ICRogueTurretInteractionHandler();
        // Save the array of arrow orientations
        this.arrowOrientations = arrowOrientations;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // Check if the Turret is ready to fire another arrow
        if (cooldownCounter <= 0) {
            // Reset the cooldown counter
            cooldownCounter = COOLDOWN;
            // Loop through the array of arrow orientations
            for (Orientation orient : arrowOrientations) {
                // Create a new arrow in the specified direction
                Arrow arrow = new Arrow(getOwnerArea(), orient,
                        getCurrentMainCellCoordinates());
                // Add the arrow to the game area
                arrow.enterArea(getOwnerArea(), this.getCurrentMainCellCoordinates());
                arrow.draw(canvas);
            }
        }
        // Decrement the cooldown counter
        cooldownCounter -= deltaTime;
    }

    @Override
    public void enterArea(Area area, DiscreteCoordinates position) {
        // Call the superclass implementation of this method
        super.enterArea(area, position);
    }

    @Override
    public void draw(Canvas canvas) {
        // Save the reference to the Canvas object
        this.canvas = canvas;
        // Draw the Turret's sprite on the screen
        sprite.draw(canvas);
    }

    @Override
    public boolean wantsCellInteraction() {
        // Return true to indicate that the Turret should respond to cell interactions
        return true;
    }

    public boolean wantsViewInteraction() {
        // Return true to indicate that the Turret should respond to view interactions
        return true;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        // Have the other interactable object interact with the Turret's interaction handler
        other.acceptInteraction(handler, isCellInteraction);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }




    private class ICRogueTurretInteractionHandler implements ICRogueInteractionHandler {
        public void interactWith(Fire fire, boolean isCellInteraction) {
            // If the Turret is interacted with a fire object, consume it
            Consume();
        }

        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {
            // If the Turret is interacted with the player in a cell interaction, consume the player
            if (isCellInteraction) {
                Consume();
            }
        }
    }
}

