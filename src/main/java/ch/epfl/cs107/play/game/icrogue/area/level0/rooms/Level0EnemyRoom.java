package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemy;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public class Level0EnemyRoom extends Level0Room implements Logic {


    List<Enemy> enemies = new ArrayList<>();

    public Level0EnemyRoom(DiscreteCoordinates givenRoomCoordinates) {
        super(givenRoomCoordinates);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }

    protected void addEnemy(Enemy givenEnemy) {
        enemies.add(givenEnemy);
    }

    @Override
    protected void createArea() {
        super.createArea();
        for (Enemy enemy : enemies) {
            enemy.enterArea(this, enemy.getCurrentCells().get(0));
        }
    }

    @Override
    public boolean isOn() {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) return false;
        }
        return super.isOn();
    }
}

