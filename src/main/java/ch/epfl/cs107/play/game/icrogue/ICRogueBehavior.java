package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
import ch.epfl.cs107.play.window.Window;

public class ICRogueBehavior extends AreaBehavior {

    public enum ICRogueCellType{
        //https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
        NONE(0, false),
        GROUND(-16777216, true),
        WALL(-14112955, false),
        HOLE(-65536, true);

        final int type;
        final boolean isWalkable;

        ICRogueCellType(int type, boolean isWalkable){
            this.type = type;
            this.isWalkable = isWalkable;
        }

        public static ICRogueBehavior.ICRogueCellType toType(int type){
            for(ICRogueBehavior.ICRogueCellType ict : ICRogueBehavior.ICRogueCellType.values()){
                if(ict.type == type)
                    return ict;
            }

            // When you add a new color, you can print the int value here before assign it to a type
            System.out.println(type);
            return NONE;
        }
    }


    public ICRogueBehavior(Window window, String name){
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width ; x++) {
                ICRogueCellType color = ICRogueCellType.toType(getRGB(height - 1 - y, x));
                setCell(x,y, new ICRogueCell(x,y,color));
            }
        }
    }

    /**
     * Cell adapted to the Tuto2 game
     */
    public class ICRogueCell extends AreaBehavior.Cell {
        private final ICRogueCellType type;

        /**
         * @author Elie BRUNO (elie.bruno@epfl.ch)
         * @Brief Small method to check if the Cell type are equal
         * @param type
         * @return if the cells are equal, a boolean
         */
        public boolean is(ICRogueCellType type) {
            return this.type == type;
        }


        /// Type of the cell following the enum

        /**
         * Default Tuto2Cell Constructor
         * @param x (int): x coordinate of the cell
         * @param y (int): y coordinate of the cell
         * @param type (EnigmeCellType), not null
         */
        public ICRogueCell(int x, int y, ICRogueCellType type){
            super(x, y);
            this.type = type;
        }


        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
            if (!type.isWalkable) {
                return false;
            }

            if (!entity.takeCellSpace()) {
                return true;
            }

            for (Interactable interactable : entities) {
                if (interactable.takeCellSpace()) {
                    return false;
                }
            }
            return true;
        }


        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
            ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
        }

    }
}

