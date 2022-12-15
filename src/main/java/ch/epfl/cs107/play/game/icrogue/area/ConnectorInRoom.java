package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * An abstraction of a room's four connectors to enable easier
 * definition and creation of the actual Connector classes.
 */
public interface ConnectorInRoom {
    int getIndex();

    DiscreteCoordinates getDestCoords();
}