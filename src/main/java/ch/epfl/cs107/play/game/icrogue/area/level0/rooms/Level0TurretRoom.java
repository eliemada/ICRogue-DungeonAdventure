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

        // Generate a random number between 0 and 100
        int randomNumber = (int)(Math.random() * 100);

        // If the random number is less than 45, register the heart actor with the game
        if (randomNumber < 45) {
            registerActor(heart);
        }

        return true;
    }

    /**
     This code creates a Heart object and sets its coordinates to random
     values between 1 and 8 (inclusive). The {@code Math.random()} method is used
     to generate random numbers. The int typecast is used to convert the
     result of {@code Math.random()} to an integer. The random values are then added
     to 1 to ensure that the resulting coordinates are between 1 and 8.

     Note: The code uses the +1 operator to ensure that the resulting
     coordinates are between 1 and 8. This is because the Math.random()
     method returns a random value between 0 (inclusive) and 1 (exclusive).
     Therefore, to generate random numbers between 1 and 8 (inclusive),
     we need to multiply the result of {@code Math.random()} by 8 and then add 1.
     */
    public Level0TurretRoom(DiscreteCoordinates givenRoomCoordinates) {
        super(givenRoomCoordinates);
        turret1 = new Turret(this,Orientation.UP, new DiscreteCoordinates(1,8),Orientation.DOWN,
                Orientation.RIGHT);
        turret2 = new Turret(this,Orientation.DOWN, new DiscreteCoordinates(8,1),Orientation.UP,
            Orientation.LEFT);
        heart = new Heart(this,Orientation.UP,new DiscreteCoordinates(
                (int)(Math.random()*8 + 1),
                (int)(Math.random()*8 + 1)
        ));
        addEnemy(turret1);
        addEnemy(turret2);

    }

}
