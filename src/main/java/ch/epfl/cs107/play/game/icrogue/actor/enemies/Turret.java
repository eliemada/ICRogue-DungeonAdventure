package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Turret extends Enemies implements Interactor {
    private static final float COOLDOWN = 2.f;
    private final ICRogueTurretInteractionHandler handler;
    Orientation[] arrowOrient;
    Arrow  arrow;
    Sprite sprite;
    private       float                           compteur = 2.f;

    public Turret(Area room, Orientation orientation, DiscreteCoordinates position,
                  Orientation...arrowOrrientation) {
        super(room, orientation, position);
        sprite  = new Sprite("icrogue/static_npc", 1f, 1f, this);
        handler = new ICRogueTurretInteractionHandler();
        arrowOrient = arrowOrrientation;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (compteur <= 0) {
            compteur = COOLDOWN;
            for (Orientation orient : arrowOrient){
                arrow    = new Arrow(getOwnerArea(), orient,
                        getCurrentMainCellCoordinates());
                arrow.enterArea(getOwnerArea(), this.getCurrentMainCellCoordinates());
            }

        }
        compteur -= deltaTime;
    }

    @Override
    public void enterArea(Area area, DiscreteCoordinates position) {
        super.enterArea(area, position);
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
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
        return true;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    private class ICRogueTurretInteractionHandler implements ICRogueInteractionHandler {
        public void interactWith(Fire fire, boolean isCellInteraction) {
            Consume();
        }

        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {
            if (isCellInteraction) {
                Consume();
            }
        }
    }
}
