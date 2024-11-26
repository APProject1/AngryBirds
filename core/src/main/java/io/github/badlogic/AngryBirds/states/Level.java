package io.github.badlogic.AngryBirds.states;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.badlogic.AngryBirds.Elements.*;
import com.badlogic.gdx.math.Vector2;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Level extends state{
    private Texture background;
    public ArrayList<Block> toRemoveBlocks;
    public ArrayList<Pig> toRemovePigs;
    private ArrayList<Bird> birds;
    int winTime;
    int loseTime;
    private ArrayList<Pig> pigs;
    public ArrayList<Block> blocks;
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
    public Bird flyingBird;
    int level;
    public World world;
    private ShapeRenderer shapeRenderer;
    ArrayList<Bird> doneBirds;
    OrthographicCamera camera;
    //public static Vector2 gravity=new Vector2(0,-9.8f);
    //private int time;



    public Level(World world,GameStateManager gsm,int num,ArrayList<Bird> birds,ArrayList<Pig> pigs,ArrayList<Block> blocks,String texture) {
        super(gsm);
        toRemoveBlocks =new ArrayList<>();
        toRemovePigs=new ArrayList<>();
        background=new Texture(texture);
        this.birds = birds;
        this.pigs = pigs;
        this.blocks = blocks;
        winTime=0;
        loseTime=0;
        pauseButtonTexture=new Texture("pausebtn.png");
        pauseButton= new Rectangle(790,510,60,50);
        slingleft=new Texture("slingleft.png");
        slingright=new Texture("slingright.png");
        this.level=num;
        slingCenter = new Vector2(167, 250); // Set the slingshot center
        slingPullPosition = new Vector2(slingCenter); // Initially at the center
        isDragging = false; // No drag initially
        shapeRenderer=new ShapeRenderer();
        leftstartpos=new Vector2(145,270);
        rightstartpos=new Vector2(190,270);
        this.world=world;
        doneBirds=new ArrayList<>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 900, 600);  // Set the camera size
        camera.update();
        // Debug renderer (to visualize Box2D bodies)
        //time=0;
        createGround();
        createSlingshot();

        // Place the first bird on the slingshot
        if (!birds.isEmpty()) {
            activeBird = birds.get(0);
            activeBird.body.setTransform(
                (slingPullPosition.x) ,
                (slingPullPosition.y) ,
                activeBird.body.getAngle() // Retain the current rotation angle
            );
        }



    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.graphics.getHeight() - Gdx.input.getY();
            if(pauseButton.contains(touchX,touchY)) {
                this.gsm.push(new PauseState(this.gsm,this));
                //dispose();
            }
            else{
                if (activeBird != null && activeBird.getBounds().contains(touchX, touchY) && !isDragging) {
                    isDragging = true;
                }

            }
        }
        else if (Gdx.input.isTouched()){
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
                    (slingPullPosition.x) ,
                    (slingPullPosition.y) ,
                    activeBird.body.getAngle() // Retain the current rotation angle
                );
                // Update bird's position
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
            //time=0;
            // Calculate launch direction and force
            Vector2 launchForce = slingCenter.cpy().sub(activeBird.body.getPosition()).scl(50000000);
            launchForce.y*=2;
            //launchForce.x*=4;
            //float force = slingCenter.dst(slingPullPosition) / maxStretch * 2; // Adjust force scaling
            //activeBird.body.setLinearVelocity(launchForce);
            activeBird.body.applyLinearImpulse(launchForce, activeBird.body.getWorldCenter(), true);
            flyingBird=activeBird;
            flyingBird.isLaunched=true;
            Music flymusic = Gdx.audio.newMusic(Gdx.files.internal("fly.mp3"));
            flymusic.setVolume(0.8f); // Set volume
            flymusic.play();
            //activeBird.velocity.x=launchDirection.x*force;
            //activeBird.velocity.y=launchDirection.y*force;
            //activeBird.launch(launchDirection.scl(force)); // Launch the bird
            //activeBird = null; // Remove the bird from the slingshot
            // Prepare the next bird
            if (!birds.isEmpty()  ){
                birds.remove(0); // Remove the launched bird
                doneBirds.add(activeBird);
                activeBird=null;
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
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (flyingBird instanceof YellowBird && !((YellowBird) flyingBird).isActivated) {
                YellowBird yellowBird = (YellowBird) flyingBird;
                yellowBird.isActivated=true;
                yellowBird.body.setLinearVelocity(new Vector2(250000000,0));
            }
            if (flyingBird instanceof BlackBird && !((BlackBird) flyingBird).isActivated) {
                BlackBird blackBird = (BlackBird) flyingBird;
                blackBird.isActivated=true;
                blackBird.width=2*blackBird.width;
                blackBird.height=2*blackBird.height;
                blackBird.body.getFixtureList().get(0).getShape().setRadius(blackBird.width/2f);
            }
        }


    }

    @Override
    public void update(float dt) {
        if (pigs.isEmpty()){
            winTime++;

            if (winTime>=240){
                this.gsm.set(new WinState(this.gsm,this.level));
            }}
        else if (!doneBirds.isEmpty() && birds.isEmpty()){
        Bird lastBird=doneBirds.get(doneBirds.size()-1);
        boolean isdone=true;
        for (Pig pig:pigs){
            if (!pig.body.getLinearVelocity().epsilonEquals(0,0)){
                isdone=false;
                break;
            }
        }
        if (isdone && birds.isEmpty() && lastBird.body.getLinearVelocity().epsilonEquals(0,0) || (lastBird.body.getPosition().x>900 || lastBird.body.getPosition().y>600 || lastBird.body.getPosition().y<0 || lastBird.body.getPosition().x<0)){
            loseTime++;

        if (loseTime>=240){
            this.gsm.set(new LoseState(this.gsm,this.level));
        }}}
        for (Block block:blocks){
            if (block instanceof WoodBlock && block.health==1){
                block.texture=new Texture("wooden1.png");
            }
            else if (block instanceof StoneBlock && block.health==2){
                block.texture=new Texture("stone2.png");
            }
            else if (block instanceof StoneBlock && block.health==1){
                block.texture=new Texture("stone1.png");
            }
        }
        for (Pig pig:pigs){
            if (pig.body.getPosition().x>900 || pig.body.getPosition().y>600 || pig.body.getPosition().y<0){
                toRemovePigs.add(pig);
            }
            if (pig instanceof MediumPig && pig.health==1){
                pig.texture=new Texture("medium1.png");
            }
            else if (pig instanceof KingPig && pig.health==2){
                pig.texture=new Texture("king2.png");
            }
            else if (pig instanceof KingPig && pig.health==1){
                pig.texture=new Texture("king1.png");
            }
        }
        for (Block block : toRemoveBlocks) {
            blocks.remove(block);
            block.texture.dispose();
            world.destroyBody(block.body);}
        toRemoveBlocks.clear();
        for (Pig pig : toRemovePigs) {
            pigs.remove(pig);
            pig.texture.dispose();
            world.destroyBody(pig.body);}
        toRemovePigs.clear();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        world.step(1/60f, 6, 2);
        world.setContactListener(new contact(this));
        sb.setProjectionMatrix(camera.combined);
        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
        sb.begin();
        sb.draw(background,0,-60,1200,800);
        sb.draw(pauseButtonTexture,790,510);
        sb.draw(slingright,160,130,40,160);
        if (flyingBird!=null && !birds.isEmpty() && (!inFrame(flyingBird)|| !isMoving(flyingBird))) {
            activeBird = birds.get(0);
            activeBird.x = slingCenter.x;
            activeBird.y = slingCenter.y;
            flyingBird=null;
            activeBird.body.setTransform(activeBird.x, activeBird.y, activeBird.body.getAngle());
        }
        for (Bird bird:birds){
            sb.draw(bird.texture,bird.body.getPosition().x-bird.height/2f,bird.body.getPosition().y-bird.width/2f,bird.width,bird.height);
        }
        for (Bird bird:doneBirds){
            sb.draw(bird.texture,bird.body.getPosition().x-bird.height/2f,bird.body.getPosition().y-bird.width/2f,bird.width,bird.height);
        }
        //if (flyingBird!=null){
            //sb.draw(flyingBird.texture,flyingBird.body.getPosition().x-flyingBird.width/2f,flyingBird.body.getPosition().y-flyingBird.width/2f,flyingBird.width,flyingBird.height);}
            /*
            time+=1;
            flyingBird.velocity.y+=gravity.y*time/20000;
            flyingBird.x+=flyingBird.velocity.x*time/100;
            flyingBird.y+=flyingBird.velocity.y*time/100;*/


        for (Block block:blocks){
            sb.draw(new TextureRegion(block.texture),block.body.getPosition().x-block.width/2f,block.body.getPosition().y-block.height/2f,block.width/2f,block.height/2f,block.width,block.height,1f,1f,(float)Math.toDegrees(block.body.getAngle()));
            //sb.draw(block.texture,block.body.getPosition().x-block.width/2f,block.body.getPosition().y-block.height/2f,block.width,block.height);
        }
        for (Pig pig:pigs){
            sb.draw(pig.texture,pig.body.getPosition().x-pig.height/2f,pig.body.getPosition().y-pig.width/2f,pig.width,pig.height);
        }
        sb.draw(slingleft,133,200,40,100);
        //sb.draw(slingright, slingCenter.x + 20, slingCenter.y, 40, 100);
        sb.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Set the line color to red
        shapeRenderer.setColor(0, 0, 0, 1);

        if (isDragging) {
            // Render the slingshot line
            shapeRenderer.line(leftstartpos.x, leftstartpos.y, slingPullPosition.x, slingPullPosition.y);
            shapeRenderer.line(rightstartpos.x, rightstartpos.y, slingPullPosition.x, slingPullPosition.y);

        }

        shapeRenderer.end();
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
        groundShape.setAsBox(870, 70); // Half-width and half-height of the ground

        // Create a fixture and attach the shape to the ground body
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 1000f; // Adjust friction as needed
        groundFixtureDef.restitution = 0.3f; // No bounce for the ground

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
        groundShape.setAsBox(7, 75); // Half-width and half-height of the ground

        // Create a fixture and attach the shape to the ground body
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f; // Adjust friction as needed
        groundFixtureDef.restitution = 0f; // No bounce for the ground

        groundBody.createFixture(groundFixtureDef);

        // Dispose of the shape
        groundShape.dispose();

        }
    public boolean inFrame(Bird bird){
        if (bird.body.getPosition().x>900 || bird.body.getPosition().y>600 || bird.body.getPosition().y<0 || bird.body.getPosition().x<0){
            return false;
        }
        return true;
    }
    public boolean isMoving(Bird bird){
        return (!bird.body.getLinearVelocity().epsilonEquals(0,0));
    }
    public void saveBirdPositions(String fileName, String timestamp) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))){
            writer.write(timestamp +", "+ level +", "+ birds.size()+1);
            writer.newLine();

            for (int i = 0; i < birds.size(); i++) {
                Bird bird = birds.get(i);
                Vector2 velocity = bird.body.getLinearVelocity();
                writer.write(bird.type+ ", " + bird.body.getPosition().x + ", " + bird.body.getPosition().y + ", " +velocity.x + ", "+velocity.y);
                writer.newLine();
            }
            //for (int i = 0; i < doneBirds.size(); i++) {
            //Bird bird = doneBirds.get(i);
            if(flyingBird!=null){
                Vector2 velocity = flyingBird.body.getLinearVelocity();
                writer.write(flyingBird.type+ ", " + flyingBird.body.getPosition().x + ", " + flyingBird.body.getPosition().y + ", " +velocity.x + ", "+velocity.y+", "+flyingBird.isActivated);
                writer.newLine();}
            else{
                writer.write("null");
                writer.newLine();}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePigPositions(String fileName, String timestamp){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(timestamp + ", " + level+ ", "+ pigs.size());
            writer.newLine();

            for(int i = 0; i < pigs.size(); i++) {
                Pig pig = pigs.get(i);
                Vector2 velocity = pig.body.getLinearVelocity();
                writer.write(pig.type + ", " + pig.body.getPosition().x + ", " + pig.body.getPosition().y + ", " + velocity.x + ", " + velocity.y+", " + pig.health);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveBlockPositions(String fileName,String timestamp) {
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(timestamp + ", " + level+", "+blocks.size());
            writer.newLine();

            for (int i = 0; i < blocks.size(); i++){
                Block block = blocks.get(i);
                //Vector2 velocity = block.body.getLinearVelocity();
                writer.write(block.type + ", " + block.body.getPosition().x + ", " + block.body.getPosition().y + ", " + block.height+ ", " + block.width+", " + block.health);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void dispose() {
        shapeRenderer.dispose();
    }
}
