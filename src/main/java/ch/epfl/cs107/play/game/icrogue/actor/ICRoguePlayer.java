package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ICRoguePlayer extends ICRogueActor {

    private String spriteName;
    private Sprite sprite;
    private static final int MOVE_DURATION = 5;

    public ICRoguePlayer(Area room, Orientation orientation, DiscreteCoordinates position,
                         String givenSpritename) {
        super(room, orientation, position);
        spriteName = givenSpritename;

        if (orientation.equals(Orientation.DOWN)) {
            //bas
            sprite = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32),
                    new Vector(.15f, -15f));
        }
        else if (orientation.equals(Orientation.UP)) {
            //haut
            sprite = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 64, 16, 32),
                    new Vector(.15f, -15.f));
        }
        else if (orientation.equals(Orientation.RIGHT)) {

            //droite
            sprite = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 32, 16, 32),
                    new Vector(.15f, -15.f));
        }
        else {
        //gauche
        sprite = new Sprite("zelda/player", .75f,1.5f,this,new RegionOfInterest(0,96,16,32),
                new Vector(.15f,-15.f));
        }
    }

    public void update(){
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        //TODO next time need to add the fireball when X is pressed
        //TODO RESET when R is pressed

    }

    private void ifKeyIsPressed(Button pressedKey){

    }
    private void moveIfPressed(Orientation orientation, Button pressedKey){
        if(pressedKey.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);

    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true ;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

    }
}
