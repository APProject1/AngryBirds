package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public abstract class Bird {
    public float x;
    public float y;
    public Texture texture;
    public int width;
    public int height;
    public Vector2 velocity;
    public Body body;

    public Bird(World world,float x, float y, String texture) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(texture);
        this.velocity=new Vector2(0,0);
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(x, y);
        this.body = world.createBody(bodyDef);
        this.body.setSleepingAllowed(false);
// Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(this.width/2f);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = 0x0001; // Default category
        fixtureDef.filter.maskBits = -1;
        fixtureDef.shape = circle;
        fixtureDef.density = 7f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.4f; // Make it bounce a little bit

// Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

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
