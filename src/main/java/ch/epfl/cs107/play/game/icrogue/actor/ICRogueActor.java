package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class ICRogueActor extends MovableAreaEntity {

    public ICRogueActor(Area room, Orientation orientation,
                        DiscreteCoordinates position){
        super(room, orientation, position);
    }
    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }

    public void leaveArea() {
        getOwnerArea().unregisterActor(this);
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells(){
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    public void draw(){};

    @Override
    public boolean takeCellSpace(){
        return false;
    }

    @Override
    public boolean isCellInteractable(){
        return true;
    }
    @Override
    public boolean isViewInteractable(){
        return false;
    }
    @Override
    public void acceptInteraction(AreaInteractionVisitor areaInteractionVisitor,
                                     boolean isCellInteraction){

    }




}
