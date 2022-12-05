package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Arrow extends Projectile{
    //Sprite sprite;
    public Arrow(Area room, Orientation orientation, DiscreteCoordinates position) {
        super(room, orientation, position,5,10);
        setSprite(new Sprite("zelda/arrow", 1f, 1f, this ,
                new RegionOfInterest(32 * orientation.ordinal(), 0, 32, 32),
                new Vector(0, 0)));
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor areaInteractionVisitor, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) areaInteractionVisitor).interactWith(this , isCellInteraction);
    }
    public void update(float deltatime){
        super.update(deltatime);
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

}
