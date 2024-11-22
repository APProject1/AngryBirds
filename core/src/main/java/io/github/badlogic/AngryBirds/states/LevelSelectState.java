package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.github.badlogic.AngryBirds.Elements.*;

import java.awt.*;
import java.util.ArrayList;

public class LevelSelectState extends state{
    private Texture background;
    private Texture level1;
    private Texture level2;
    private Texture level3;
    private Rectangle level1Button;
    private Rectangle level2Button;
    private Rectangle level3Button;
    public static ArrayList<Level> levels;
    //private ShapeRenderer shape;
    private BitmapFont font1;
    private Texture back;
    private Rectangle backButton;

    public LevelSelectState(GameStateManager gsm){
        super(gsm);
        level1=new Texture("level1.png");
        level2=new Texture("level2.png");
        level3=new Texture("level3.png");
        back=new Texture("backbutton.png");
        level1Button=new Rectangle(130,220,150,120);
        level2Button=new Rectangle(380,220,150,120);
        level3Button=new Rectangle(630,220,150,120);
        backButton=new Rectangle(50,500,50,50);
        background= new Texture("bg2.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
        levels=new ArrayList<>();
        levels.add(level1setup(gsm));
        levels.add(level2setup(gsm));
        levels.add(level3setup(gsm));
        //shape=new ShapeRenderer();

    }
    static public Level level1setup(GameStateManager gsm){
        World world=new World(new Vector2(0,-20),true);
        ArrayList<Bird> birds=new ArrayList<>();
        ArrayList<Block> blocks=new ArrayList<>();
        ArrayList<Pig> pigs=new ArrayList<>();
        birds.add(new RedBird(world,163,252));
        birds.add(new RedBird(world,50,130));
        birds.add(new BlackBird(world,95,130));
        blocks.add(new WoodBlock(world,652,160,70,70,1));
        blocks.add(new WoodBlock(world,788,160,70,70,1));
        blocks.add(new WoodBlock(world,684,230,70,70,1));
        blocks.add(new WoodBlock(world,754,230,70,70,1));
        blocks.add(new WoodBlock(world,717,300,70,70,1));
        pigs.add(new MediumPig(world,730,130));
        pigs.add(new SmallPig(world,645,200));
        pigs.add(new SmallPig(world,825,200));
        pigs.add(new SmallPig(world,730,275));
        pigs.add(new KingPig(world,720,338));
        Level level=new Level(world,gsm,1,birds,pigs,blocks,"level1bg.png");
        return level;
    }
    static public Level level2setup(GameStateManager gsm){
        World world=new World(new Vector2(0,-20),true);
        ArrayList<Bird> birds=new ArrayList<>();
        ArrayList<Block> blocks=new ArrayList<>();
        ArrayList<Pig> pigs=new ArrayList<>();
        birds.add(new YellowBird(world,143,252));
        birds.add(new YellowBird(world,40,130));
        birds.add(new RedBird(world,85,130));
        blocks.add(new GlassBlock(world,652,160,70,70,2));
        blocks.add(new GlassBlock(world,788,160,70,70,2));
        blocks.add(new GlassBlock(world,684,230,70,70,2));
        blocks.add(new GlassBlock(world,754,230,70,70,2));
        blocks.add(new GlassBlock(world,717,300,70,70,2));
        pigs.add(new SmallPig(world,800,270));
        pigs.add(new SmallPig(world,645,270));
        pigs.add(new SmallPig(world,720,135));
        pigs.add(new SmallPig(world,720,275));
        pigs.add(new KingPig(world,710,338));
        pigs.add(new MediumPig(world,715,205));
        Level level=new Level(world,gsm,2,birds,pigs,blocks,"level2bg.png");
        return level;
    }
    static public Level level3setup(GameStateManager gsm){
        World world=new World(new Vector2(0,-20),true);
        ArrayList<Bird> birds=new ArrayList<>();
        ArrayList<Block> blocks=new ArrayList<>();
        ArrayList<Pig> pigs=new ArrayList<>();
        birds.add(new BlackBird(world,143,252));
        birds.add(new BlackBird(world,40,130));
        birds.add(new YellowBird(world,85,130));
        blocks.add(new StoneBlock(world,652,160,70,70,3));
        blocks.add(new StoneBlock(world,788,160,70,70,3));
        blocks.add(new StoneBlock(world,684,230,70,70,3));
        blocks.add(new StoneBlock(world,754,230,70,70,3));
        blocks.add(new StoneBlock(world,717,300,70,70,3));
        //pigs.add(new MediumPig(world,710,240));
        //pigs.add(new SmallPig(world,642,240));
        //pigs.add(new SmallPig(world,792,240));
        //pigs.add(new MediumPig(world,707,307));
        //pigs.add(new SmallPig(world,712,380));
        pigs.add(new MediumPig(world,722,140));
        Level level=new Level(world,gsm,3,birds,pigs,blocks,"level2bg.png");
        return level;
    }
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.graphics.getHeight() - Gdx.input.getY();
            if(level1Button.contains(touchX,touchY)){
                this.gsm.push(levels.get(0));
                //dispose();
            }
            if(level2Button.contains(touchX,touchY)){
                this.gsm.push(levels.get(1));
                //dispose();
            }
            if(level3Button.contains(touchX,touchY)){
                this.gsm.push(levels.get(2));
                //dispose();
            }
            if(backButton.contains(touchX,touchY)){
                this.gsm.set(new MenuState(gsm));
                dispose();
            }

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,900,600);
        sb.draw(back,50,500,50,50);
        sb.draw(level1,130,220,150,120);
        sb.draw(level2,380,220,150,120);
        sb.draw(level3,630,220,150,120);
        font1.getData().setScale(1.2f);
        font1.draw(sb,"CHOOSE LEVEL",230,540);
        sb.end();
        //shape.begin(ShapeRenderer.ShapeType.Line);
        //shape.setColor(1, 0, 0, 1);
        //shape.rect(backButton.x, backButton.y, backButton.width, backButton.height);
        //shape.end();
    }


    @Override
    public void dispose() {
        background.dispose();
        font1.dispose();
    }
}
