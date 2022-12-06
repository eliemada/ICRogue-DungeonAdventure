package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;

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
    private final DiscreteCoordinates POS_ARRIVAL = new DiscreteCoordinates(4, 4);

    /**
     * Add all the areas
     */
    private void initLevel() {
        currentLevel = new Level0();
        currentLevel.registerRooms(this);
        // TODO what does forceBegin mean?
        setCurrentArea(currentLevel.getTitleStartRoom(), false);
        player = new ICRoguePlayer(getCurrentArea(), Orientation.UP, POS_ARRIVAL);
        player.enterArea(getCurrentArea(), POS_ARRIVAL);
        keyboard= getCurrentArea().getKeyboard();
    }

    private void switchRoom() {
        // TODO find better way to hand down destArea and coords from connector to ICRogue
        setCurrentArea(player.getDestArea(), false);
        player.enterArea(getCurrentArea(), POS_ARRIVAL);
        currentRoom = (ICRogueRoom) getCurrentArea();
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
        if (player.isBetweenRooms()) {
            switchRoom();
        }
        if(currentLevel.isOn()){
            System.out.println("Win!");
            end();
        }
        if(!player.isAlive()){
            gameOver();
            end();
        }
        // TODO delete below tests
//        if ((currentRoom != null) && !currentRoom.isOff()) System.out.println("!isOff");
    }

    public void gameOver(){
        String gameOver = ".----------------.  .----------------.  .----------------.  .----------------.         .----------------.  .----------------.  .----------------.  .----------------. \n"
                          + "| .--------------. || .--------------. || .--------------. || .--------------. |       | .--------------. || .--------------. || .--------------. || .--------------. |\n"
                          + "| |    ______    | || |      __      | || | ____    ____ | || |  _________   | |       | |     ____     | || | ____   ____  | || |  _________   | || |  _______     | |\n"
                          + "| |  .' ___  |   | || |     /  \\     | || ||_   \\  /   _|| || | |_   ___  |  | |       | |   .'    `.   | || ||_  _| |_  _| | || | |_   ___  |  | || | |_   __ \\    | |\n"
                          + "| | / .'   \\_|   | || |    / /\\ \\    | || |  |   \\/   |  | || |   | |_  \\_|  | |       | |  /  .--.  \\  | || |  \\ \\   / /   | || |   | |_  \\_|  | || |   | |__) |   | |\n"
                          + "| | | |    ____  | || |   / ____ \\   | || |  | |\\  /| |  | || |   |  _|  _   | |       | |  | |    | |  | || |   \\ \\ / /    | || |   |  _|  _   | || |   |  __ /    | |\n"
                          + "| | \\ `.___]  _| | || | _/ /    \\ \\_ | || | _| |_\\/_| |_ | || |  _| |___/ |  | |       | |  \\  `--'  /  | || |    \\ ' /     | || |  _| |___/ |  | || |  _| |  \\ \\_  | |\n"
                          + "| |  `._____.'   | || ||____|  |____|| || ||_____||_____|| || | |_________|  | |       | |   `.____.'   | || |     \\_/      | || | |_________|  | || | |____| |___| | |\n"
                          + "| |              | || |              | || |              | || |              | |       | |              | || |              | || |              | || |              | |\n"
                          + "| '--------------' || '--------------' || '--------------' || '--------------' |       | '--------------' || '--------------' || '--------------' || '--------------' |\n"
                          + " '----------------'  '----------------'  '----------------'  "
                          + "'----------------'         '----------------'  '----------------'  "
                          + "'----------------'  '----------------' \n";
        for(int i=0; i<gameOver. length(); i++) {
            System.out.print(gameOver.charAt(i));
        }
    }

    public void showCredits(){
        String end = " ________       ___    ___              _______    ___        ___   _______           "
                   + "________   ________   ___  ___   ________    ________                                                                               \n"
                     + "|\\   __  \\     |\\  \\  /  /|            |\\  ___ \\  |\\  \\      |\\  \\ |\\  ___ \\         |\\   __  \\ |\\   __  \\ |\\  \\|\\  \\ |\\   ___  \\ |\\   __  \\                                                                              \n"
                     + "\\ \\  \\|\\ /_    \\ \\  \\/  / /            \\ \\   __/| \\ \\  \\     \\ \\  \\\\ \\   __/|        \\ \\  \\|\\ /_\\ \\  \\|\\  \\\\ \\  \\\\\\  \\\\ \\  \\\\ \\  \\\\ \\  \\|\\  \\                                                                             \n"
                     + " \\ \\   __  \\    \\ \\    / /              \\ \\  \\_|/__\\ \\  \\     \\ \\  \\\\ \\  \\_|/__       \\ \\   __  \\\\ \\   _  _\\\\ \\  \\\\\\  \\\\ \\  \\\\ \\  \\\\ \\  \\\\\\  \\                                                                            \n"
                     + "  \\ \\  \\|\\  \\    \\/  /  /                \\ \\  \\_|\\ \\\\ \\  \\____ \\ \\  \\\\ \\  \\_|\\ \\       \\ \\  \\|\\  \\\\ \\  \\\\  \\|\\ \\  \\\\\\  \\\\ \\  \\\\ \\  \\\\ \\  \\\\\\  \\                                                                           \n"
                     + "   \\ \\_______\\ __/  / /                   \\ \\_______\\\\ \\_______\\\\ \\__\\\\ \\_______\\       \\ \\_______\\\\ \\__\\\\ _\\ \\ \\_______\\\\ \\__\\\\ \\__\\\\ \\_______\\                                                                          \n"
                     + "    \\|_______||\\___/ /                     \\|_______| \\|_______| \\|__| \\|_______|        \\|_______| \\|__|\\|__| \\|_______| \\|__| \\|__| \\|_______|                                                                          \n"
                     + "              \\|___|/                                                                                                                                                                                                     \n"
                     + " ________   ________    ________              ________   _______    ________   ________   ________   _________   ___   ________   ________           ___  __     ___  ___   ________   ___        _______    ________     \n"
                     + "|\\   __  \\ |\\   ___  \\ |\\   ___ \\            |\\   ____\\ |\\  ___ \\  |\\   __  \\ |\\   __  \\ |\\   ____\\ |\\___   ___\\|\\  \\ |\\   __  \\ |\\   ___  \\        |\\  \\|\\  \\  |\\  \\|\\  \\ |\\   ____\\ |\\  \\      |\\  ___ \\  |\\   __  \\    \n"
                     + "\\ \\  \\|\\  \\\\ \\  \\\\ \\  \\\\ \\  \\_|\\ \\           \\ \\  \\___|_\\ \\   __/| \\ \\  \\|\\ /_\\ \\  \\|\\  \\\\ \\  \\___|_\\|___ \\  \\_|\\ \\  \\\\ \\  \\|\\  \\\\ \\  \\\\ \\  \\       \\ \\  \\/  /|_\\ \\  \\\\\\  \\\\ \\  \\___| \\ \\  \\     \\ \\   __/| \\ \\  \\|\\  \\   \n"
                     + " \\ \\   __  \\\\ \\  \\\\ \\  \\\\ \\  \\ \\\\ \\           \\ \\_____  \\\\ \\  \\_|/__\\ \\   __  \\\\ \\   __  \\\\ \\_____  \\    \\ \\  \\  \\ \\  \\\\ \\   __  \\\\ \\  \\\\ \\  \\       \\ \\   ___  \\\\ \\  \\\\\\  \\\\ \\  \\  ___\\ \\  \\     \\ \\  \\_|/__\\ \\   _  _\\  \n"
                     + "  \\ \\  \\ \\  \\\\ \\  \\\\ \\  \\\\ \\  \\_\\\\ \\           \\|____|\\  \\\\ \\  \\_|\\ \\\\ \\  \\|\\  \\\\ \\  \\ \\  \\\\|____|\\  \\    \\ \\  \\  \\ \\  \\\\ \\  \\ \\  \\\\ \\  \\\\ \\  \\       \\ \\  \\\\ \\  \\\\ \\  \\\\\\  \\\\ \\  \\|\\  \\\\ \\  \\____ \\ \\  \\_|\\ \\\\ \\  \\\\  \\| \n"
                     + "   \\ \\__\\ \\__\\\\ \\__\\\\ \\__\\\\ \\_______\\            ____\\_\\  \\\\ \\_______\\\\ \\_______\\\\ \\__\\ \\__\\ ____\\_\\  \\    \\ \\__\\  \\ \\__\\\\ \\__\\ \\__\\\\ \\__\\\\ \\__\\       \\ \\__\\\\ \\__\\\\ \\_______\\\\ \\_______\\\\ \\_______\\\\ \\_______\\\\ \\__\\\\ _\\ \n"
                     + "    \\|__|\\|__| \\|__| \\|__| \\|_______|           |\\_________\\\\|_______| \\|_______| \\|__|\\|__||\\_________\\    \\|__|   \\|__| \\|__|\\|__| \\|__| \\|__|        \\|__| \\|__| \\|_______| \\|_______| \\|_______| \\|_______| \\|__|\\|__|\n"
                     + "                                                \\|_________|                      "
                     + "          \\|_________|                                                                                                                 ";
        for(int i=0; i<end.length(); i++) {
            System.out.print(end.charAt(i));
        }
    }

    @Override
    public void end() {
        showCredits();
        System.exit(0);
    }

    @Override
    public String getTitle() {
        return "ICRogue";
    }


}
