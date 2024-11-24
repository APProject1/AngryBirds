package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class StoneBlock extends Block{
    public StoneBlock(World world, float x, float y, int height, int width,int level) {
        super(world,x,y,"stoneblock.png",3,width,height,level);
        this.width=width;
        this.height=height;
        type="StoneBlock";
    }
    public StoneBlock(World world, float x, float y, int height, int width,int health, int level) {
        super(world,x,y,"stoneblock.png",health,width,height,level);
        this.width=width;
        this.height=height;
        type="StoneBlock";
    }
    @Override
    public void takeDamage() {
    }
}
