package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;
import java.util.ArrayList;

public abstract class ICRogueRoom extends Area implements Logic {

    private ICRogueBehavior behavior;
    private String behaviorName;
    private DiscreteCoordinates roomCoordinates;
    private Connector[] roomConnectors = new Connector[4];
    private Keyboard keyboard;

    // this is a flag to prevent unnecessary calls from update() to the Logic interface
    private boolean wasOn = false;

    public ICRogueRoom(ArrayList<DiscreteCoordinates> connectorsCoordinates, ArrayList<Orientation> orientations,
                       String givenBehaviorName, DiscreteCoordinates givenRoomCoordinates) {

        behaviorName = givenBehaviorName;
        roomCoordinates = givenRoomCoordinates;

        for (int i = 0; i < roomConnectors.length; i++){
            // connectorInRoom has the opposite orientation of Connector (important for the sprite)
            roomConnectors[i] = new Connector(this, orientations.get(i).opposite(), connectorsCoordinates.get(i));
        }

    }

    public Connector[] getRoomConnectors(){
        return roomConnectors;
    }

    public DiscreteCoordinates getRoomCoordinates(){
        return roomCoordinates;
    }

    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected void createArea(){
        for (Connector connector : roomConnectors){
            registerActor(connector);
        }
    }

    /// EnigmeArea extends Area




    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICRogueBehavior(window, behaviorName);
            setBehavior(behavior);
            createArea();
            keyboard = getKeyboard();
            return true;
        }
        return false;
    }
    public String getBehaviorName(){
        return behaviorName;
    }

    public void update(float deltaTime){
        super.update(deltaTime);
        if (!wasOn && isOn()) {
            for (Connector connector : roomConnectors) connector.open();
            wasOn = true;
        }

        // TODO delete below cheat codes after testing
//        if (keyboard.get(Keyboard.O).isPressed()){
//            for (Connector connector : roomConnectors){
//                connector.setState(Connector.State.OPEN);
//            }
//        }
//        if (keyboard.get(Keyboard.L).isPressed()){
//            roomConnectors[0].lockWithKey(1);
//        }
//
//        if (keyboard.get(Keyboard.T).isPressed()){
//            for (Connector connector : roomConnectors){
//                if (connector.getState() == Connector.State.CLOSED){
//                    connector.setState(Connector.State.OPEN);
//                }
//                else if (connector.getState() == Connector.State.OPEN){
//                    connector.setState(Connector.State.CLOSED);
//                }
//            }
//        }
    }


    /**
     * @return (boolean): true if the signal is considered as on
     */
    @Override
    public boolean isOn() {
        return super.getWasVisited();
    }

    /**
     * @return (boolean): true if the signal is considered as off
     */
    @Override
    public boolean isOff() {
        return !isOn();
    }

    /**
     * @return (float) : the signal intensity, usually 0.0 or 1.0
     */
    @Override
    public float getIntensity() {
        return isOn() ? 1.0f : 0.0f;
    }

}

