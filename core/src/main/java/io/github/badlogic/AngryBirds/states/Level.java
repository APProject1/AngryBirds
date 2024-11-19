package io.github.badlogic.AngryBirds.states;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.badlogic.AngryBirds.Elements.Bird;
import io.github.badlogic.AngryBirds.Elements.Block;
import io.github.badlogic.AngryBirds.Elements.Pig;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Level extends state{
    private Texture background;
    private ArrayList<Bird> birds;
    private ArrayList<Pig> pigs;
    private ArrayList<Block> blocks;
    private Texture pauseButtonTexture;
    private Rectangle pauseButton;
    private Texture slingleft;
    private Texture slingright;
    private Vector2 slingCenter; // Center of the slingshot
    private Vector2 slingPullPosition; // Current pull position
    private boolean isDragging; // To track if dragging is happening
    private float maxStretch = 100f; // Max distance for pulling the slingshot
    private Bird activeBird;
    private Vector2 leftstartpos;
    private Vector2 rightstartpos;
    private Bird flyingBird;
    int level;
    private World world;
    private ShapeRenderer shapeRenderer;
    private Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    public static Vector2 gravity=new Vector2(0,-9.8f);
    private int time;


    public Level(World world,GameStateManager gsm,int num,ArrayList<Bird> birds,ArrayList<Pig> pigs,ArrayList<Block> blocks,String texture) {
        super(gsm);
        background=new Texture(texture);
        this.birds = birds;
        this.pigs = pigs;
        this.blocks = blocks;
        pauseButtonTexture=new Texture("pausebtn.png");
        pauseButton= new Rectangle(790,510,60,50);
        slingleft=new Texture("slingleft.png");
        slingright=new Texture("slingright.png");
        this.level=num;
        slingCenter = new Vector2(147, 250); // Set the slingshot center
        slingPullPosition = new Vector2(slingCenter); // Initially at the center
        isDragging = false; // No drag initially
        shapeRenderer=new ShapeRenderer();
        leftstartpos=new Vector2(145,270);
        rightstartpos=new Vector2(190,270);
        this.world=world;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 900, 600);  // Set the camera size
        camera.update();
        // Debug renderer (to visualize Box2D bodies)
        debugRenderer = new Box2DDebugRenderer();
        time=0;
        createGround();
        createSlingshot();

        // Place the first bird on the slingshot
        if (!birds.isEmpty()) {
            activeBird = birds.get(0);
            activeBird.x = slingCenter.x;
            activeBird.y = slingCenter.y;
        }



    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.graphics.getHeight() - Gdx.input.getY();
            if(pauseButton.contains(touchX,touchY)) {
                this.gsm.set(new PauseState(this.gsm,this));
                dispose();
            }
            else{
                if (activeBird != null && activeBird.getBounds().contains(touchX, touchY) && !isDragging) {
                    isDragging = true;
                }

            }
        }
        if (Gdx.input.isTouched()){
            if (isDragging){
                float dragX = Gdx.input.getX();
                float dragY = Gdx.graphics.getHeight() - Gdx.input.getY();
                Vector2 dragPosition = new Vector2(dragX, dragY);
                Vector2 dragVector = dragPosition.sub(slingCenter);
                if (dragVector.len() > maxStretch) {
                    dragVector.nor().scl(maxStretch);
                }
                slingPullPosition.set(slingCenter.cpy().add(dragVector));
                activeBird.body.setTransform(
                    (slingPullPosition.x-activeBird.width/2f) ,
                    (slingPullPosition.y-activeBird.height/2f) ,
                    activeBird.body.getAngle() // Retain the current rotation angle
                );
                // Update bird's position

                System.out.println("Rendering slingshot lines...");
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

                // Set line color (you can change the color)
                shapeRenderer.setColor(5, 0, 0, 1);

                //shapeRenderer.line(slingCenter.x, slingCenter.y, activeBird.x, activeBird.y);

                // Draw the right rubber band
                //shapeRenderer.line(slingCenter.x, slingCenter.y, activeBird.x, activeBird.y);

                // End drawing lines
                shapeRenderer.end();
                //drawSlingshot();
            }
        }
        if (!Gdx.input.isTouched() && isDragging){
            isDragging = false;
            time=0;
            // Calculate launch direction and force
            Vector2 launchForce = slingCenter.cpy().sub(slingPullPosition);
            launchForce.x*=100000;
            launchForce.y*=100000;
            //float force = slingCenter.dst(slingPullPosition) / maxStretch * 2; // Adjust force scaling
            //activeBird.body.setLinearVelocity(launchForce);
            activeBird.body.applyLinearImpulse(launchForce, activeBird.body.getWorldCenter(), true);
            //activeBird.velocity.x=launchDirection.x*force;
            //activeBird.velocity.y=launchDirection.y*force;
            //activeBird.launch(launchDirection.scl(force)); // Launch the bird
            //activeBird = null; // Remove the bird from the slingshot

            // Prepare the next bird
            if (!birds.isEmpty()) {
                birds.remove(0); // Remove the launched bird
                flyingBird=activeBird;
                if (!birds.isEmpty()) {
                    activeBird = birds.get(0);
                    activeBird.x = slingCenter.x;
                    activeBird.y = slingCenter.y;
                }
            }
        }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                this.gsm.set(new WinState(this.gsm,this.level));
                dispose();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                this.gsm.set(new LoseState(this.gsm,this.level));
                dispose();
        }


    }

    @Override
    public void update(float dt) {

        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        world.step(1/60f, 6, 2);
        sb.setProjectionMatrix(camera.combined);
        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
        sb.begin();
        sb.draw(background,0,-60,1200,800);
        sb.draw(pauseButtonTexture,790,510);
        sb.draw(slingright,160,130,40,160);
        for (Bird bird:birds){
            sb.draw(bird.texture,bird.body.getPosition().x-bird.width/2f,bird.body.getPosition().y,bird.width,bird.height);
        }
        if (flyingBird!=null){
            sb.draw(flyingBird.texture,flyingBird.body.getPosition().x-flyingBird.width,flyingBird.body.getPosition().y,flyingBird.width,flyingBird.height);
            /*
            time+=1;
            flyingBird.velocity.y+=gravity.y*time/20000;
            flyingBird.x+=flyingBird.velocity.x*time/100;
            flyingBird.y+=flyingBird.velocity.y*time/100;*/

        }
        for (Block block:blocks){
            sb.draw(block.texture,block.body.getPosition().x-block.width/2f,block.body.getPosition().y-block.height/2f,block.width,block.height);
        }
        for (Pig pig:pigs){
            sb.draw(pig.texture,pig.x,pig.y,pig.width,pig.height);
        }
        sb.draw(slingleft,133,200,40,100);
        //sb.draw(slingright, slingCenter.x + 20, slingCenter.y, 40, 100);
        sb.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Set the line color to red
        shapeRenderer.setColor(1, 0, 0, 1);

        if (isDragging) {
            // Render the slingshot line
            shapeRenderer.line(leftstartpos.x, leftstartpos.y, slingPullPosition.x, slingPullPosition.y);
            shapeRenderer.line(rightstartpos.x, rightstartpos.y, slingPullPosition.x, slingPullPosition.y);

        }

        shapeRenderer.end();
        debugRenderer.render(world, camera.combined);
    }
    /*
    public void drawSlingshot(){
        System.out.println("Rendering slingshot lines...");
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setAutoShapeType(true);
        // Set line color (you can change the color)
        shapeRenderer.setColor(1, 0, 0, 1); // Red color for the line
        // Calculate dynamic positions based on the bird's or mouse's position
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();
        float stretchedX = slingCenter.x + (mouseX - slingCenter.x) * 0.1f;  // 0.1f is a scaling factor for the stretch
        float stretchedY = slingCenter.y + (mouseY - slingCenter.y) * 0.1f;  // Adjust to match the bird's pulling motion

        // Draw the left rubber band
        shapeRenderer.line(slingCenter.x, slingCenter.y, stretchedX, stretchedY);

        // Draw the right rubber band
        shapeRenderer.line(slingCenter.x, slingCenter.y, stretchedX, stretchedY);

        // End drawing lines
        shapeRenderer.end();
    }*/
    private void createGround() {
        // Define a static body for the ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(50, 60); // Set the ground's position (centered)
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        // Create the ground body in the world
        Body groundBody =this.world.createBody(groundBodyDef);

        // Define the shape of the ground
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(800, 70); // Half-width and half-height of the ground

        // Create a fixture and attach the shape to the ground body
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f; // Adjust friction as needed
        groundFixtureDef.restitution = 0f; // No bounce for the ground

        groundBody.createFixture(groundFixtureDef);

        // Dispose of the shape
        groundShape.dispose();
    }
    private void createSlingshot() {
        // Left arm of the slingshot
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(170, 150); // Set the ground's position (centered)
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Create the ground body in the world
        Body groundBody =this.world.createBody(bodyDef);

        // Define the shape of the ground
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(20, 70); // Half-width and half-height of the ground

        // Create a fixture and attach the shape to the ground body
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f; // Adjust friction as needed
        groundFixtureDef.restitution = 0f; // No bounce for the ground

        groundBody.createFixture(groundFixtureDef);

        // Dispose of the shape
        groundShape.dispose();

        }
    public void dispose() {
        shapeRenderer.dispose();
    }
}
