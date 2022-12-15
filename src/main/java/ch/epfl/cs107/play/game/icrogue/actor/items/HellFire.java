package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

/**
 * HellFire is a stationary fire that damages the player on contact.
 * It is implemented as an item as it does not move like a projectile.
 */
public class HellFire extends Item {
    private final int DAMAGE = 2;
    private Sprite [] sprites = Sprite.extractSprites("zelda/fire",
            7, 1, 1,
            this , 16, 16);

    Animation animation = new Animation(3,sprites);
    public HellFire(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        setSprite(new Sprite("icrogue/cherry", 0.6f, 0.6f, this));
    }

    @Override
    public void update(float deltatime){
        super.update(deltatime);
        animation.update(deltatime);
    }
    @Override
    public void draw(Canvas canvas) {
        if (!isCollected()) animation.draw(canvas);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this,isCellInteraction);
    }

    public int getDamage() {
        return DAMAGE;
    }
}

