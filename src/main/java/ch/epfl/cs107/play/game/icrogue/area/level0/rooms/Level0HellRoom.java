package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemy;
import ch.epfl.cs107.play.game.icrogue.actor.items.HellFire;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A room with stationary fires that damage the player.
 */
public class Level0HellRoom extends Level0ItemRoom implements Logic {

    /**
     * Default constructor.
     * @param givenRoomCoordinates the coordinates of the room in the level
     */
    public Level0HellRoom(DiscreteCoordinates givenRoomCoordinates) {
        super(givenRoomCoordinates);
        makeHellBurn();
    }

    /**
     * Creates 20 hellfires (items) in a room in pseudorandom positions.
     */
    private void makeHellBurn(){
        ArrayList<DiscreteCoordinates> possibleCoordinates = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                possibleCoordinates.add(new DiscreteCoordinates(i, j));
            }
        }
        ArrayList<DiscreteCoordinates> coords = RandomHelper.chooseKInList(20, possibleCoordinates);
        for (DiscreteCoordinates pos : coords) addItem(new HellFire(this, Orientation.UP, pos));
    }

    @Override
    public boolean isOn() {
        return super.isOn();
    }


}
