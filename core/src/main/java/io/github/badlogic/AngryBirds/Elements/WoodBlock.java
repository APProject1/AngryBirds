package io.github.badlogic.AngryBirds.Elements;

public class WoodBlock extends Block{
    public WoodBlock(float x, float y,int height,int width) {
        super(x,y,"woodblock.png",2);
        this.height=height;
        this.width=width;
    }

    @Override
    public void takeDamage() {
    }
}
