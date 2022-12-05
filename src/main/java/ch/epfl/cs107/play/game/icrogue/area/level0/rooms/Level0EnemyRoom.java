package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemies;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

import java.util.ArrayList;
import java.util.List;

public class Level0EnemyRoom extends Level0Room implements Logic {
    List<Enemies> enemies = new ArrayList<>();
    public Level0EnemyRoom(DiscreteCoordinates givenRoomCoordinates) {
        super(givenRoomCoordinates);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }

    protected void addEnemy(Enemies givenEnemy){
        enemies.add(givenEnemy);
    }

    @Override
    protected void createArea() {
        super.createArea();
        for (Enemies enemiesList : enemies){
            enemiesList.enterArea(this,enemiesList.getCurrentCells().get(0));
        }
    }

    @Override
    public boolean isOn() {
        return enemies.size()==0;
    }

    @Override
    public boolean isOff() {
        return !isOn();
    }

}
