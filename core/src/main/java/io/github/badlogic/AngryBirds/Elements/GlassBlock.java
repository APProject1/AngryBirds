package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class GlassBlock extends Block{
    public GlassBlock(World world, float x, float y, int height, int width,int level) {
        super(world,x,y,"glassblock.png",1,width,height,level);
        this.height=height;
        this.width=width;
        type="GlassBlock";
    }
    public GlassBlock(World world, float x, float y, int height, int width,int health,int level) {
        super(world,x,y,"glassblock.png",health,width,height,level);
        this.height=height;
        this.width=width;
        type="GlassBlock";
    }

    @Override
    public void takeDamage() {
    }
}
