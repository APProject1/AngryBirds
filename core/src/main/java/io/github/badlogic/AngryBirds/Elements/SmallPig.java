package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class SmallPig extends Pig {
    public SmallPig(World world, float x, float y) {
        super(world,x, y, "smallpig.png", 1,40 );
        height=40;
        width=40;
        type="SmallPig";
    }
    public SmallPig(World world, float x, float y, float velX, float velY, int health) {
        super(world,x, y, velX,velY, "smallpig.png", health,40 );
        height=40;
        width=40;
        type="SmallPig";
    }

    @Override
    public void takeDamage() {

    }
}
