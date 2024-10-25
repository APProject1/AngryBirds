package io.github.badlogic.AngryBirds.Elements;

public class StoneBlock extends Block{
    public StoneBlock(float x, float y,int height,int width) {
        super(x,y,"stoneblock.png",3);
        this.width=width;
        this.height=height;
    }
    @Override
    public void takeDamage() {
    }
}
