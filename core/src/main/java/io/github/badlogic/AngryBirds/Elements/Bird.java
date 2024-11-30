package io.github.badlogic.AngryBirds.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public abstract class Bird {
    public float x;
    public float y;
    public String type;
    public Texture texture;
    public int width;
    public int height;
    public Vector2 velocity;
    public Body body;
    public Fixture fixture;
    public World world;
    public boolean isLaunched;
    public boolean isActivated;
    public FixtureDef fixtureDef;
    public Bird(World world,float x, float y, String texture) {
        this.x = x;
        this.y = y;
        this.world=world;
        isLaunched=false;
        this.texture = new Texture(texture);
        this.velocity=new Vector2(0,0);
        BodyDef bodyDef = new BodyDef();
        isActivated=false;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y+20);
        this.body = world.createBody(bodyDef);
        this.body.setSleepingAllowed(false);
        CircleShape circle = new CircleShape();
        circle.setRadius(this.width+20);
        body.setUserData(this);

        fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = 0x0002;
        fixtureDef.filter.maskBits =0x0001|0x0003;
        fixtureDef.shape = circle;
        fixtureDef.density = 40f;
        fixtureDef.friction = 0.001f;
        fixtureDef.restitution = 0.0001f;
        this.body.setFixedRotation(true);
// Create our fixture and attach it to the body
        this.fixture = body.createFixture(fixtureDef);
    }

    public Bird(World world,float x, float y, float velX, float velY,String texture){
        this.world=world;
        this.x=x;
        this.y=y;
        //isLaunched=false;
        this.texture=new Texture(texture);
        //this.velocity=new Vector2(0,0);
        BodyDef bodyDef = new BodyDef();
        isActivated=false;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y+20);
        this.body = world.createBody(bodyDef);
        this.body.setSleepingAllowed(false);
        CircleShape circle = new CircleShape();
        circle.setRadius(this.width+20);
        body.setUserData(this);
        fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = 0x0002; // Default category
        fixtureDef.filter.maskBits =0x0001|0x0003;
        fixtureDef.shape = circle;
        fixtureDef.density = 40f;
        fixtureDef.friction = 0.001f;
        fixtureDef.restitution = 0.0001f; // Make it bounce a little bit
        this.body.setFixedRotation(true);
        this.fixture = body.createFixture(fixtureDef);

        this.body.setLinearVelocity(velX,velY);
    }
    public Bird(World world,float x, float y, float velX, float velY){
        this.world=world;
        this.x=x;
        this.y=y;
        //isLaunched=false;
        //this.texture=new Texture(texture);
        //this.velocity=new Vector2(0,0);
        BodyDef bodyDef = new BodyDef();
        isActivated=false;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y+20);
        this.body = world.createBody(bodyDef);
        this.body.setSleepingAllowed(false);
        CircleShape circle = new CircleShape();
        circle.setRadius(this.width+20);
        body.setUserData(this);
        fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = 0x0002;
        fixtureDef.filter.maskBits =0x0001|0x0003;
        fixtureDef.shape = circle;
        fixtureDef.density = 40f;
        fixtureDef.friction = 0.001f;
        fixtureDef.restitution = 0.0001f;
        this.body.setFixedRotation(true);
        this.fixture = body.createFixture(fixtureDef);

        this.body.setLinearVelocity(velX,velY);
    }
    public void launch(){
    };

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle(body.getPosition().x-20, body.getPosition().y-20, width+30, height+30);
    }


}
