package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class KingPig extends Pig {
    public KingPig(World world,float x, float y) {
        super(world,x,y,"kingpig.png",3,70);
        height=85;
        width=70;
        type="KingPig";
    }
    public KingPig(World world,float x, float y, float velX, float velY, int health){
        super(world,x,y,velX, velY,"kingpig.png",health,70);
        height=85;
        width=70;
        type="KingPig";
    }
    public KingPig(World world,float x, float y, float velX, float velY, int health, boolean isTest){
        super(world,x,y,velX, velY,health,70);
        height=85;
        width=70;
        type="KingPig";
    }
    @Override
    public void takeDamage() {
    }
}
