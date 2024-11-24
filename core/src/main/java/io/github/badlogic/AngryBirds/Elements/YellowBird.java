package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Bird{

    public YellowBird(World world, float x, float y) {
        super(world,x,y,"yellowbird.png");
        height=40;
        type="YellowBird";
        width=45;
    }
    public YellowBird(World world, float x, float y, float velX, float velY){
        super(world,x,y,velX,velY,"yellowbird.png");
        height=40;
        type="YellowBird";
        width=45;
    }
}
