package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.badlogic.AngryBirds.states.Level;

public abstract class Block {
    public float x;
    public float y;
    public Texture texture;
    public int height;
    public String type;
    public int width;
    public int health;
    public Body body; // Box2D body
    public int level;
    private World world;

    public Block(World world, float x, float y, String texturePath, int health, int width, int height,int level) {
        this.x = x;
        this.y = y;
        this.world=world;
        this.texture = new Texture(texturePath);
        this.health = health;
        this.width = width;
        this.height = height;
        this.level=level;
        // Create a dynamic Box2D body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Dynamic body type
        bodyDef.position.set(x, y); // Convert to meters using PPM

        // Create the body in the world
        this.body = world.createBody(bodyDef);
        // Define the shape (rectangle for a block)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2f, height/2f); // Box dimensions in meters
        this.body.setSleepingAllowed(false);
        // Define the fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = 0x0003; // Default category
        fixtureDef.filter.maskBits =0x0001|0x0002;
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; // Adjust density for mass
        fixtureDef.friction = 0.1f; // Adjust friction for sliding resistance
        fixtureDef.restitution = 0.00000001f; // Adjust restitution for bounciness
        //this.body.setLinearDamping(777);
        this.body.setFixedRotation(true);
        body.setUserData(this);
        // Attach the shape to the body
        body.createFixture(fixtureDef);
        shape.dispose();
    }



    public abstract void takeDamage();

    public void applyForce(float forceX, float forceY) {
        body.applyForceToCenter(new Vector2(forceX, forceY), true); // Apply force to the center
    }

    public void dispose(Level level) {
        level.toRemoveBlocks.add(this);


    }
}
