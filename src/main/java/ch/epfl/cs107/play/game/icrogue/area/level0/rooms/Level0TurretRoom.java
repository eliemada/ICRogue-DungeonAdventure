package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Heart;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Level0TurretRoom extends Level0EnemyRoom{

    private Turret turret1;
    private Turret turret2;

    private Heart heart;

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        super.begin(window, fileSystem);
        registerActor(heart);
        return true;
    }

    public Level0TurretRoom(DiscreteCoordinates givenRoomCoordinates) {
        super(givenRoomCoordinates);
        turret1 = new Turret(this,Orientation.UP, new DiscreteCoordinates(1,8),Orientation.DOWN,
                Orientation.RIGHT);
        turret2 = new Turret(this,Orientation.DOWN, new DiscreteCoordinates(8,1),Orientation.UP,
            Orientation.LEFT);
        heart = new Heart(this,Orientation.UP,new DiscreteCoordinates(3,3));
        addEnemy(turret1);
        addEnemy(turret2);

    }

}
