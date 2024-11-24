package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class WoodBlock extends Block{
    public WoodBlock(World world, float x, float y, int height, int width,int level) {
        super(world,x,y,"woodblock.png",2,width,height,level);
        this.height=height;
        this.width=width;
        type="WoodBlock";
    }
    public WoodBlock(World world, float x, float y, int height, int width,int health,int level) {
        super(world,x,y,"woodblock.png",health,width,height,level);
        this.height=height;
        this.width=width;
        type="WoodBlock";
    }
    @Override
    public void takeDamage(){
    }
}
