package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.Playable;
import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class HpBar extends Entity {

    private Sprite  frame = new Sprite("HPbar",3,0.4f,this,new RegionOfInterest(0,0,300,400));
    private Sprite hpBar = new Sprite("redBar",2.4f,0.39f,this,null,new Vector(0.5f,0));
    private float ratio;
    private ICRoguePlayer player;

    /**
     * Default Entity constructor
     *
     * @param position (Coordinate): Initial position of the entity. Not null
     */
    public HpBar(Vector position, ICRoguePlayer player) {
        super(position);
        ratio = hpBar.getWidth()/player.getHp();
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        hpBar.setWidth(player.getHp()*ratio);
    }

    @Override
    public void draw(Canvas canvas) {
        hpBar.draw(canvas);
        frame.draw(canvas);

    }
}
