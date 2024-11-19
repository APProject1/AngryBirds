package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class BlackBird extends Bird{
    public BlackBird(World world, float x, float y) {
        super(world,x,y,"blackbird.png");
        height=49;
        width=40;
    }

}
