package io.github.badlogic.AngryBirds.states;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FallingObject {
    private Texture texture;
    private float x, y, speed;
    public boolean isOnGround;
    private static final float OBJECT_WIDTH = 50;  // Fixed width for all textures
    private static final float OBJECT_HEIGHT = 50;
    private float gravity = -9.8f; // Gravity constant
    private float bounceFactor = 0.5f;
    private float velocityY;

    public FallingObject(Texture texture, float x, float y, float speed) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.isOnGround = false;
        this.velocityY = 0;// Initially not on the ground
    }

    public void update(float dt) {
        if (!isOnGround) {
            y -= speed * dt;
            velocityY += gravity * dt; // Apply gravity
            y += velocityY * dt;// Move the object down
            if (y <=80) { // If it hits the ground (or a certain Y value)
                y = 80;  // Set the object to rest at the ground level
                if (Math.abs(velocityY) < 0.1f) {
                    isOnGround = true;// If velocity is very small, reset position
                    //reset((float) Math.random() * 800);
                } else {
                    velocityY = -velocityY * bounceFactor; // Reverse velocity and apply bounce factor
                }
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(texture, x, y,OBJECT_WIDTH, OBJECT_HEIGHT);
    }

    public Texture getTexture() {
        return texture;
    }
    public void reset(float newX) {
        // Once an object is on the ground, reset its position to start falling again
        this.x = newX;
        this.y = 600;
        this.velocityY = 0;
        this.isOnGround = false;
    }
}
