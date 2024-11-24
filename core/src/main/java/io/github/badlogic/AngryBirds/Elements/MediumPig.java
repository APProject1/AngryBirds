package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class MediumPig extends Pig {
    public MediumPig(World world, float x, float y) {
        super(world,x, y, "mediumpig.png", 2,55);
        height=55;
        width=55;
        type="MediumPig";
    }
    public MediumPig(World world, float x, float y, float velX, float velY, int health){
        super(world,x, y, velX, velY, "mediumpig.png", health,55);
        height=55;
        width=55;
        type="MediumPig";
    }
    @Override
    public void takeDamage() {
    }
}
