package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird{
    public RedBird(World world, float x, float y) {
        super(world,x,y,"redbird1.png");
        width=40;
        height=40;
        type="RedBird";

    }

}
