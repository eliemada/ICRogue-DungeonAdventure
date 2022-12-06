package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

public abstract class Level0ItemRoom extends Level0Room {
    private ArrayList<Item> items = new ArrayList<>();

    public Level0ItemRoom(DiscreteCoordinates givenRoomCoordinates) {
        super(givenRoomCoordinates);
    }

    protected void addItem(Item item){
        items.add(item);
    }
    @Override
    protected void createArea() {
        super.createArea();
        for (Item item : items){
            registerActor(item);
        }
    }

    @Override
    public boolean isOn() {
        for (Item item : items){
            if (!item.isCollected()) return false;
        }
        return super.isOn();
    }

    // other methods from the Logic Interface call the above.
}
