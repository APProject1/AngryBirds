package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.graphics.Texture;

public abstract class Pig {
    public float x;
    public float y;
    public Texture texture;
    public int health;
    public int height;
    public int width;

    public Pig(float x, float y, String texture, int health) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(texture);
        this.health = health;
    }
    public abstract void takeDamage();
    public void dispose() {
        texture.dispose();
    }
}
