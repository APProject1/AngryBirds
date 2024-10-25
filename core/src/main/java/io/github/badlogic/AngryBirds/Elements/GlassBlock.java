package io.github.badlogic.AngryBirds.Elements;

public class GlassBlock extends Block{
    public GlassBlock(float x, float y,int height,int width) {
        super(x,y,"glassblock.png",1);
        this.height=height;
        this.width=width;
    }

    @Override
    public void takeDamage() {
    }
}
