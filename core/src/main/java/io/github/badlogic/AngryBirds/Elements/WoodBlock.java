package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class WoodBlock extends Block{
    public WoodBlock(World world, float x, float y, int height, int width) {
        super(world,x,y,"woodblock.png",2,width,height);
        this.height=height;
        this.width=width;
    }

    @Override
    public void takeDamage() {
    }
}
