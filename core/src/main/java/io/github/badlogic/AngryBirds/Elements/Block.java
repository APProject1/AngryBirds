package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.graphics.Texture;

public abstract class Block {
    public float x;
    public float y;
    public Texture texture;
    public int height;
    public int width;
    public int health;

    public Block(float x, float y, String texture, int health) {
        this.x=x;
        this.y=y;
        this.texture=new Texture(texture);
        this.health=health;
    }
    public abstract void takeDamage();
    public void dispose() {
        texture.dispose();
    }
}
