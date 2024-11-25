package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.badlogic.AngryBirds.Elements.*;

import java.io.*;
import java.util.*;

public class SavedGamesState extends state{
    private Texture background;
    private BitmapFont font1;
    private BitmapFont font2;
    private Stage stage;
    private Button backButton;
    private TextButton.TextButtonStyle textButtonStyle;
    public ArrayList<Bird> resetBirds;
    public ArrayList<Pig> resetPigs;
    public ArrayList<Block> resetBlocks;
    private String level1;
    private String level2;
    private String level3;
    //Level levelInstance;

    public SavedGamesState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg2.png");
        font1 = new BitmapFont(Gdx.files.internal("font3.fnt"));
        font2 = new BitmapFont(Gdx.files.internal("font3.fnt"));

        String level1="level1bg.png";
        String level2="level2bg.png";
        String level3="level3bg.png";

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        backButton = createButton("backbutton.png", 50, 500, 50, 50);
        stage.addActor(backButton);
        createTimestampButtons("saves/birdPositions.txt",stage);

        backButton.addListener(event -> {
            if (backButton.isPressed()) {
                this.gsm.set(new MenuState(gsm));
                dispose();
                return true;
            }
            return false;
        });
    }

    public void createTimestampButtons(String fileName, Stage stage) {
        Map<String, Integer> timestampToLevelMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] parts = line.split(", ");
                    if (parts.length > 1) {
                        String timestamp = parts[0].trim();
                        if (!timestamp.equals("RedBird") && !timestamp.equals("BlackBird") && !timestamp.equals("YellowBird")) {
                            int level = Integer.parseInt(parts[1].trim());
                            timestampToLevelMap.put(timestamp, level);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        float yPosition = 350;
        float buttonHeight = 50;
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font2;
        font2.getData().setScale(0.5f);

        for (Map.Entry<String, Integer> entry : timestampToLevelMap.entrySet()) {
            String timestamp = entry.getKey();
            int level = entry.getValue();

            TextButton button = new TextButton(timestamp, style);
            button.setPosition(350, yPosition);
            button.setSize(200, buttonHeight);
            stage.addActor(button);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Timestamp selected: " + timestamp);
                    World world = new World(new Vector2(0, -20), true);
                    readBirdPositions("saves/birdPositions.txt", timestamp,world);
                    readBlockPositions("saves/blockPositions.txt", timestamp,world);
                    readPigPositions("saves/pigPositions.txt", timestamp,world);
                    System.out.println("Birds loaded: " + resetBirds.size());
                    System.out.println("Pigs loaded: " + resetPigs.size());
                    System.out.println("Blocks loaded: " + resetBlocks.size());


                    if(level==1){
                        try {
                            Level levelInstance = new Level(world, gsm, level, resetBirds, resetPigs, resetBlocks,"level1bg.png");
                            //levelInstance.flyingBird=resetBirds.get(resetBirds.size()-1);
                            gsm.set(levelInstance);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dispose();
                    }
                    if(level==2){
                        try {
                            Level levelInstance = new Level(world, gsm, level, resetBirds, resetPigs, resetBlocks,"level2bg.png");
                            //levelInstance.flyingBird=resetBirds.get(resetBirds.size()-1);
                            gsm.set(levelInstance);
                        }catch (Exception e) {
                            e.printStackTrace();}
                        dispose();
                    }
                    if(level==3){
                        try {
                            Level levelInstance = new Level(world, gsm, level, resetBirds, resetPigs, resetBlocks,"level2bg.png");
                            //levelInstance.flyingBird=resetBirds.get(resetBirds.size()-1);
                            gsm.set(levelInstance);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        dispose();
                    }
                }
            });
            yPosition -= buttonHeight + 10;
        }
    }


    public void readBirdPositions(String fileName, String selectedTimestamp, World world) {
        try(BufferedReader reader=new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isTimestampFound=false;
            int level;
            int n=0;
            resetBirds=new ArrayList<>();

            while((line=reader.readLine()) != null) {
                if (line.contains(selectedTimestamp)) {
                    isTimestampFound = true;
                    String[] parts=line.split(", ");
                    if(parts.length>1){
                        level=Integer.parseInt(parts[1].trim());
                        n=Integer.parseInt(parts[2].trim());
                    }
                    continue;
                }
                if(isTimestampFound){
                    String[] birdDetails = line.split(", ");
                    if (birdDetails.length == 5) {
                        String type = birdDetails[0].trim();
                        float posX = Float.parseFloat(birdDetails[1].trim());
                        float posY = Float.parseFloat(birdDetails[2].trim());
                        float velX = Float.parseFloat(birdDetails[3].trim());
                        float velY = Float.parseFloat(birdDetails[4].trim());
                        System.out.println("type: " + type + ", posX: " + posX + ", posY: " + posY + ", velX: " + velX + ", velY: " + velY);
                        if(type.equals("RedBird")){
                            resetBirds.add(new RedBird(world,posX,posY,velX,velY));
                            System.out.println("yesss");
                        }
                        if(type.equals("YellowBird")){
                            resetBirds.add(new YellowBird(world,posX,posY,velX,velY));
                        }
                        if(type.equals("BlackBird")){
                            resetBirds.add(new BlackBird(world,posX,posY,velX,velY));
                        }
                    }
                    if(resetBirds.size() ==n) {
                        break;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readPigPositions(String fileName, String selectedTimestamp, World world) {
        try (BufferedReader reader=new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isTimestampFound=false;
            int level;
            int n=0;
            resetPigs=new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.contains(selectedTimestamp)) {
                    isTimestampFound = true;
                    String[] parts = line.split(", ");
                    if (parts.length > 2) {
                        level=Integer.parseInt(parts[1].trim());
                        n=Integer.parseInt(parts[2].trim());
                    }
                    continue;
                }

                if (isTimestampFound) {
                    String[] pigDetails = line.split(", ");
                    if (pigDetails.length == 6) {
                        String type = pigDetails[0].trim();
                        float posX = Float.parseFloat(pigDetails[1].trim());
                        float posY = Float.parseFloat(pigDetails[2].trim());
                        float velX = Float.parseFloat(pigDetails[3].trim());
                        float velY = Float.parseFloat(pigDetails[4].trim());
                        int health = Integer.parseInt(pigDetails[5].trim());
                        System.out.println("type: " + type + ", posX: " + posX + ", posY: " + posY + ", velX: " + velX + ", velY: " + velY +" "+health);
                        if(type.equals("KingPig")){
                            resetPigs.add(new KingPig(world, posX, posY, velX, velY, health));
                        }
                        if(type.equals("MediumPig")){
                            resetPigs.add(new MediumPig(world, posX, posY, velX, velY, health));
                        }
                        if(type.equals("SmallPig")){
                            resetPigs.add(new SmallPig(world, posX, posY, velX, velY, health));
                        }
                    }
                    if(resetPigs.size()==n){
                        break;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readBlockPositions(String fileName, String selectedTimestamp, World world) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isTimestampFound = false;
            int level=0;
            int n=0;
            resetBlocks = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.contains(selectedTimestamp)) {
                    isTimestampFound = true;
                    String[] parts = line.split(", ");
                    if(parts.length>2){
                        level = Integer.parseInt(parts[1].trim());
                        n=Integer.parseInt(parts[2].trim());
                    }
                    continue;
                }

                if (isTimestampFound) {
                    String[] blockDetails = line.split(", ");
                    if (blockDetails.length == 6) {
                        String type = blockDetails[0].trim();
                        float posX = Float.parseFloat(blockDetails[1].trim());
                        float posY = Float.parseFloat(blockDetails[2].trim());
                        int height = (int)Float.parseFloat(blockDetails[3].trim());
                        int width = (int)Float.parseFloat(blockDetails[4].trim());
                        int health = Integer.parseInt(blockDetails[5].trim());
                        System.out.println("type: " + type + ", posX: " + posX + ", posY: " + posY +" "+ height+" "+width+" "+health );
                        if(type.equals("StoneBlock")) {
                            resetBlocks.add(new StoneBlock(world, posX, posY, height, width, health, level));
                        }
                        if(type.equals("WoodBlock")) {
                            resetBlocks.add(new WoodBlock(world, posX, posY, height, width, health, level));
                        }
                        if(type.equals("GlassBlock")) {
                            resetBlocks.add(new GlassBlock(world, posX, posY, height, width, health, level));
                        }
                    }
                    if(resetBlocks.size()==n){
                        break;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void resize(int width,int height){
        stage.getViewport().update(width,height, true);
    }

    private Button createButton(String texturePath, float x, float y, float width, float height) {
        Texture texture = new Texture(texturePath);
        TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
        Button button = new Button(drawable);
        button.setBounds(x, y, width, height);
        return button;
    }

    @Override
    protected void handleInput() {
        // Input is handled by the stage and button listeners
    }

    @Override
    public void update(float dt){
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb){
        sb.begin();
        sb.draw(background, 0, 0, 900, 600);
        font1.getData().setScale(1.2f);
        font1.draw(sb, "SAVED GAMES", 240, 540);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        font1.dispose();
        font2.dispose();
        stage.dispose();
    }
}
