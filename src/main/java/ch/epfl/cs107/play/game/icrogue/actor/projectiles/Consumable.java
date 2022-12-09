package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

public interface Consumable {
    // Change the internal state of the object to reflect that it has been consumed
    void consume();

    // Check whether the object has been consumed
    boolean isConsumed();
}