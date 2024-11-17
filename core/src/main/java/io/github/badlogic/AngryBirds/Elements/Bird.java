package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public abstract class Bird {
    public float x;
    public float y;
    public Texture texture;
    public int width;
    public int height;
    public Vector2 velocity;

    public Bird(float x, float y, String texture) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(texture);
        this.velocity=new Vector2(0,0);
    }
    public void launch(){

    };

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
