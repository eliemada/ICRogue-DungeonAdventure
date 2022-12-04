package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;

import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICRogue extends AreaGame {
    public final static float      CAMERA_SCALE_FACTOR = 13.f;
    private     Keyboard        keyboard;
    private     Level           currentLevel;
    private     ICRogueRoom     currentRoom;
    private     ICRoguePlayer   player;

    /**
     * Add all the areas
     */
    private void initLevel() {
        currentLevel = new Level0();
        currentLevel.registerRooms(this);
        setCurrentArea(currentLevel.getTitleStartRoom(), false);
        DiscreteCoordinates coords = new DiscreteCoordinates(2, 2);
        player = new ICRoguePlayer(getCurrentArea(), Orientation.UP, coords);
        player.enterArea(getCurrentArea(), coords);
        keyboard= getCurrentArea().getKeyboard();
    }


    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            initLevel();
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (keyboard.get(keyboard.R).isPressed()){
            player.leaveArea();
            initLevel();
        }



    }

    @Override
    public void end() {
    }

    @Override
    public String getTitle() {
        return "ICRogue";
    }


}
