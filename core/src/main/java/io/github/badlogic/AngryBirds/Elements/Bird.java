package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.graphics.Texture;

public abstract class Bird {
    public float x;
    public float y;
    public Texture texture;
    public int width;
    public int height;

    public Bird(float x, float y, String texture) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(texture);
    }
    public abstract void launch();

    public void dispose() {
        texture.dispose();
    }
}
