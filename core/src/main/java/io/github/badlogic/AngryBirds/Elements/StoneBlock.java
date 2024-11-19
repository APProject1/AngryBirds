package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.physics.box2d.World;

public class StoneBlock extends Block{
    public StoneBlock(World world, float x, float y, int height, int width) {
        super(world,x,y,"stoneblock.png",3,width,height);
        this.width=width;
        this.height=height;
    }
    @Override
    public void takeDamage() {
    }
}
