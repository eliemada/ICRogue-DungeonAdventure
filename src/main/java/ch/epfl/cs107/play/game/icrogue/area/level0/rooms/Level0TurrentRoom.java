package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Enemies;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.List;

public class Level0TurrentRoom extends Level0EnemyRoom{

    private Turret turret1;
    private Turret turret2;

    public Level0TurrentRoom(DiscreteCoordinates givenRoomCoordinates) {
        super(givenRoomCoordinates);
        turret1 = new Turret(this,Orientation.UP, new DiscreteCoordinates(1,8),Orientation.DOWN,
                Orientation.RIGHT);
        turret2 = new Turret(this,Orientation.DOWN, new DiscreteCoordinates(8,1),Orientation.UP,
            Orientation.LEFT);
        addEnemy(turret1);
        addEnemy(turret2);
    }

}
