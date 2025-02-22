package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import io.github.badlogic.AngryBirds.states.Level;

public abstract class Pig {
    public float x;
    public float y;
    public Texture texture;
    public int health;
    public int height;
    public int width;
    public Body body;
    public String type;
    public Pig(World world,float x, float y, float velX, float velY, int health,int width){
        this.x = x;
        this.y = y;
        //this.texture = new Texture(texture);
        this.health = health;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        this.body = world.createBody(bodyDef);
        this.body.setSleepingAllowed(false);
        CircleShape circle = new CircleShape();
        circle.setRadius(width/2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = 0x0001; // Default category
        fixtureDef.filter.maskBits =0x0002|0x0003;
        fixtureDef.shape = circle;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.001f;
        fixtureDef.restitution = 0.0001f; // Make it bounce a little bit
        this.body.setFixedRotation(true);
        body.setUserData(this);
        Fixture fixture = body.createFixture(fixtureDef);
        this.body.setLinearVelocity(velX,velY);
    }

    public Pig(World world,float x, float y, String texture, int health,int width){
        this.x = x;
        this.y = y;
        this.texture = new Texture(texture);
        this.health = health;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        this.body = world.createBody(bodyDef);
        this.body.setSleepingAllowed(false);
        CircleShape circle = new CircleShape();
        circle.setRadius(width/2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = 0x0001; // Default category
        fixtureDef.filter.maskBits =0x0002|0x0003;
        fixtureDef.shape = circle;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.001f;
        fixtureDef.restitution = 0.0001f; // Make it bounce a little bit
        this.body.setFixedRotation(true);
        body.setUserData(this);
        Fixture fixture = body.createFixture(fixtureDef);
    }
    public Pig(World world,float x, float y, float velX, float velY, String texture, int health,int width){
        this.x = x;
        this.y = y;
        this.texture = new Texture(texture);
        this.health = health;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        this.body = world.createBody(bodyDef);
        this.body.setSleepingAllowed(false);
        CircleShape circle = new CircleShape();
        circle.setRadius(width/2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = 0x0001; // Default category
        fixtureDef.filter.maskBits =0x0002|0x0003;
        fixtureDef.shape = circle;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.001f;
        fixtureDef.restitution = 0.0001f;
        this.body.setFixedRotation(true);
        body.setUserData(this);
        Fixture fixture = body.createFixture(fixtureDef);
        this.body.setLinearVelocity(velX,velY);
    }
    public abstract void takeDamage();
    public void dispose(Level level) {
        level.toRemovePigs.add(this);


    }
}
