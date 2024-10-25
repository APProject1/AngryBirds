package io.github.badlogic.AngryBirds.Elements;

public class MediumPig extends Pig {
    public MediumPig(float x, float y) {
        super(x, y, "mediumpig.png", 2);
        height=55;
        width=55;
    }

    @Override
    public void takeDamage() {
    }
}
