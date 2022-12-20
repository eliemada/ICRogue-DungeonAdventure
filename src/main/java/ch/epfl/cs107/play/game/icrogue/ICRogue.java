package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.HpBar;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;

import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

/**
 * ICRogue is the main class of the game
 */
public class ICRogue extends AreaGame {
    public final static float      CAMERA_SCALE_FACTOR = 13.f;
    private     Keyboard        keyboard;
    private     Level           currentLevel;
    private     ICRoguePlayer   player;
    private final DiscreteCoordinates POS_ARRIVAL = new DiscreteCoordinates(4, 4);
    private HpBar hpBar;
    private Level0 level;


    /**
     * Initialize the game: Loads the first level and creates the player.
     */
    private void initLevel() {
        level = new Level0();
        level.registerRooms(this);
        setCurrentArea(level.getTitleStartRoom(), false);
        player = new ICRoguePlayer(getCurrentArea(), Orientation.UP, POS_ARRIVAL);
        player.enterArea(getCurrentArea(), POS_ARRIVAL);
        keyboard= getCurrentArea().getKeyboard();
        hpBar = new HpBar(new Vector(0,9.5f),player);
    }

    /**
     * Switches the player to a room that is given by the connector he is standing in.
     */
    private void switchRoom() {
        setCurrentArea(player.getInsideConnector().getDestArea(), false);
        player.enterArea(getCurrentArea(), player.getInsideConnector().getDestCoords());
    }

    /**
     * Starts the game.
     * @param window     (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return (boolean): true if the game starts correctly
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            initLevel();
            return true;
        }
        return false;
    }

    /**
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (keyboard.get(keyboard.R).isPressed()){
            player.leaveArea();
            initLevel();
        }
        if (player.getInsideConnector() != null) {
            switchRoom();
        }
        if(level.isOn()){
            System.out.println("Win!");
            end();
        }
        if(!player.isAlive()){
            gameOver();
            end();
        }
        getCurrentArea().registerActor(hpBar);

        hpBar.update(deltaTime);

    }

    public void gameOver(){
        String gameOver = """
                .----------------.  .----------------.  .----------------.  .----------------.         .----------------.  .----------------.  .----------------.  .----------------.\s
                | .--------------. || .--------------. || .--------------. || .--------------. |       | .--------------. || .--------------. || .--------------. || .--------------. |
                | |    ______    | || |      __      | || | ____    ____ | || |  _________   | |       | |     ____     | || | ____   ____  | || |  _________   | || |  _______     | |
                | |  .' ___  |   | || |     /  \\     | || ||_   \\  /   _|| || | |_   ___  |  | |       | |   .'    `.   | || ||_  _| |_  _| | || | |_   ___  |  | || | |_   __ \\    | |
                | | / .'   \\_|   | || |    / /\\ \\    | || |  |   \\/   |  | || |   | |_  \\_|  | |       | |  /  .--.  \\  | || |  \\ \\   / /   | || |   | |_  \\_|  | || |   | |__) |   | |
                | | | |    ____  | || |   / ____ \\   | || |  | |\\  /| |  | || |   |  _|  _   | |       | |  | |    | |  | || |   \\ \\ / /    | || |   |  _|  _   | || |   |  __ /    | |
                | | \\ `.___]  _| | || | _/ /    \\ \\_ | || | _| |_\\/_| |_ | || |  _| |___/ |  | |       | |  \\  `--'  /  | || |    \\ ' /     | || |  _| |___/ |  | || |  _| |  \\ \\_  | |
                | |  `._____.'   | || ||____|  |____|| || ||_____||_____|| || | |_________|  | |       | |   `.____.'   | || |     \\_/      | || | |_________|  | || | |____| |___| | |
                | |              | || |              | || |              | || |              | |       | |              | || |              | || |              | || |              | |
                | '--------------' || '--------------' || '--------------' || '--------------' |       | '--------------' || '--------------' || '--------------' || '--------------' |
                 '----------------'  '----------------'  '----------------'  '----------------'         '----------------'  '----------------'  '----------------'  '----------------'\s
                """;
        for(int i=0; i<gameOver. length(); i++) {
            System.out.print(gameOver.charAt(i));
        }
    }

    public void showCredits(){
        String end = """
                 ________       ___    ___              _______    ___        ___   _______           ________   ________   ___  ___   ________    ________                                                                              \s
                |\\   __  \\     |\\  \\  /  /|            |\\  ___ \\  |\\  \\      |\\  \\ |\\  ___ \\         |\\   __  \\ |\\   __  \\ |\\  \\|\\  \\ |\\   ___  \\ |\\   __  \\                                                                             \s
                \\ \\  \\|\\ /_    \\ \\  \\/  / /            \\ \\   __/| \\ \\  \\     \\ \\  \\\\ \\   __/|        \\ \\  \\|\\ /_\\ \\  \\|\\  \\\\ \\  \\\\\\  \\\\ \\  \\\\ \\  \\\\ \\  \\|\\  \\                                                                            \s
                 \\ \\   __  \\    \\ \\    / /              \\ \\  \\_|/__\\ \\  \\     \\ \\  \\\\ \\  \\_|/__       \\ \\   __  \\\\ \\   _  _\\\\ \\  \\\\\\  \\\\ \\  \\\\ \\  \\\\ \\  \\\\\\  \\                                                                           \s
                  \\ \\  \\|\\  \\    \\/  /  /                \\ \\  \\_|\\ \\\\ \\  \\____ \\ \\  \\\\ \\  \\_|\\ \\       \\ \\  \\|\\  \\\\ \\  \\\\  \\|\\ \\  \\\\\\  \\\\ \\  \\\\ \\  \\\\ \\  \\\\\\  \\                                                                          \s
                   \\ \\_______\\ __/  / /                   \\ \\_______\\\\ \\_______\\\\ \\__\\\\ \\_______\\       \\ \\_______\\\\ \\__\\\\ _\\ \\ \\_______\\\\ \\__\\\\ \\__\\\\ \\_______\\                                                                         \s
                    \\|_______||\\___/ /                     \\|_______| \\|_______| \\|__| \\|_______|        \\|_______| \\|__|\\|__| \\|_______| \\|__| \\|__| \\|_______|                                                                         \s
                              \\|___|/                                                                                                                                                                                                    \s
                 ________   ________    ________              ________   _______    ________   ________   ________   _________   ___   ________   ________           ___  __     ___  ___   ________   ___        _______    ________    \s
                |\\   __  \\ |\\   ___  \\ |\\   ___ \\            |\\   ____\\ |\\  ___ \\  |\\   __  \\ |\\   __  \\ |\\   ____\\ |\\___   ___\\|\\  \\ |\\   __  \\ |\\   ___  \\        |\\  \\|\\  \\  |\\  \\|\\  \\ |\\   ____\\ |\\  \\      |\\  ___ \\  |\\   __  \\   \s
                \\ \\  \\|\\  \\\\ \\  \\\\ \\  \\\\ \\  \\_|\\ \\           \\ \\  \\___|_\\ \\   __/| \\ \\  \\|\\ /_\\ \\  \\|\\  \\\\ \\  \\___|_\\|___ \\  \\_|\\ \\  \\\\ \\  \\|\\  \\\\ \\  \\\\ \\  \\       \\ \\  \\/  /|_\\ \\  \\\\\\  \\\\ \\  \\___| \\ \\  \\     \\ \\   __/| \\ \\  \\|\\  \\  \s
                 \\ \\   __  \\\\ \\  \\\\ \\  \\\\ \\  \\ \\\\ \\           \\ \\_____  \\\\ \\  \\_|/__\\ \\   __  \\\\ \\   __  \\\\ \\_____  \\    \\ \\  \\  \\ \\  \\\\ \\   __  \\\\ \\  \\\\ \\  \\       \\ \\   ___  \\\\ \\  \\\\\\  \\\\ \\  \\  ___\\ \\  \\     \\ \\  \\_|/__\\ \\   _  _\\ \s
                  \\ \\  \\ \\  \\\\ \\  \\\\ \\  \\\\ \\  \\_\\\\ \\           \\|____|\\  \\\\ \\  \\_|\\ \\\\ \\  \\|\\  \\\\ \\  \\ \\  \\\\|____|\\  \\    \\ \\  \\  \\ \\  \\\\ \\  \\ \\  \\\\ \\  \\\\ \\  \\       \\ \\  \\\\ \\  \\\\ \\  \\\\\\  \\\\ \\  \\|\\  \\\\ \\  \\____ \\ \\  \\_|\\ \\\\ \\  \\\\  \\|\s
                   \\ \\__\\ \\__\\\\ \\__\\\\ \\__\\\\ \\_______\\            ____\\_\\  \\\\ \\_______\\\\ \\_______\\\\ \\__\\ \\__\\ ____\\_\\  \\    \\ \\__\\  \\ \\__\\\\ \\__\\ \\__\\\\ \\__\\\\ \\__\\       \\ \\__\\\\ \\__\\\\ \\_______\\\\ \\_______\\\\ \\_______\\\\ \\_______\\\\ \\__\\\\ _\\\s
                    \\|__|\\|__| \\|__| \\|__| \\|_______|           |\\_________\\\\|_______| \\|_______| \\|__|\\|__||\\_________\\    \\|__|   \\|__| \\|__|\\|__| \\|__| \\|__|        \\|__| \\|__| \\|_______| \\|_______| \\|_______| \\|_______| \\|__|\\|__|
                                                                \\|_________|                                \\|_________|                                                                                                                \s""";
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
