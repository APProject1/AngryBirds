package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    protected ArrayList<Level> levels;
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
        ArrayList<Bird> birds=new ArrayList<>();
        ArrayList<Block> blocks=new ArrayList<>();
        ArrayList<Pig> pigs=new ArrayList<>();
        birds.add(new RedBird(143,252));
        birds.add(new RedBird(40,130));
        birds.add(new BlackBird(85,130));
        blocks.add(new WoodBlock(652,130,70,70));
        blocks.add(new WoodBlock(788,130,70,70));
        blocks.add(new WoodBlock(684,200,70,70));
        blocks.add(new WoodBlock(754,200,70,70));
        blocks.add(new WoodBlock(717,270,70,70));
        pigs.add(new MediumPig(730,130));
        pigs.add(new SmallPig(645,200));
        pigs.add(new SmallPig(825,200));
        pigs.add(new SmallPig(730,275));
        pigs.add(new KingPig(720,338));
        Level level=new Level(gsm,1,birds,pigs,blocks,"level1bg.png");
        return level;
    }
    static public Level level2setup(GameStateManager gsm){
        ArrayList<Bird> birds=new ArrayList<>();
        ArrayList<Block> blocks=new ArrayList<>();
        ArrayList<Pig> pigs=new ArrayList<>();
        birds.add(new YellowBird(143,252));
        birds.add(new YellowBird(40,130));
        birds.add(new RedBird(85,130));
        blocks.add(new StoneBlock(637,130,70,50));
        blocks.add(new GlassBlock(647,200,70,30));
        blocks.add(new WoodBlock(707,130,70,70));
        blocks.add(new WoodBlock(707,200,70,70));
        blocks.add(new WoodBlock(707,270,70,70));
        blocks.add(new StoneBlock(792,130,70,50));
        blocks.add(new GlassBlock(802,200,70,30));
        blocks.add(new WoodBlock(572,130,50,50));
        blocks.add(new WoodBlock(842,130,50,50));
        pigs.add(new SmallPig(800,270));
        pigs.add(new SmallPig(645,270));
        pigs.add(new SmallPig(720,135));
        pigs.add(new SmallPig(720,275));
        pigs.add(new KingPig(710,338));
        pigs.add(new MediumPig(715,205));
        Level level=new Level(gsm,2,birds,pigs,blocks,"level2bg.png");
        return level;
    }
    static public Level level3setup(GameStateManager gsm){
        ArrayList<Bird> birds=new ArrayList<>();
        ArrayList<Block> blocks=new ArrayList<>();
        ArrayList<Pig> pigs=new ArrayList<>();
        birds.add(new BlackBird(143,252));
        birds.add(new BlackBird(40,130));
        birds.add(new YellowBird(85,130));
        blocks.add(new WoodBlock(682,130,110,110));
        blocks.add(new StoneBlock(792,130,70,40));
        blocks.add(new StoneBlock(642,130,70,40));
        blocks.add(new GlassBlock(792,200,40,40));
        blocks.add(new GlassBlock(642,200,40,40));
        blocks.add(new StoneBlock(767,240,60,20));
        blocks.add(new StoneBlock(687,240,60,20));
        blocks.add(new WoodBlock(695,300,80,80));
        pigs.add(new MediumPig(710,240));
        pigs.add(new SmallPig(642,240));
        pigs.add(new SmallPig(792,240));
        pigs.add(new MediumPig(707,307));
        pigs.add(new SmallPig(712,380));
        pigs.add(new KingPig(703,140));
        Level level=new Level(gsm,3,birds,pigs,blocks,"level2bg.png");
        return level;
    }
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.graphics.getHeight() - Gdx.input.getY();
            if(level1Button.contains(touchX,touchY)){
                this.gsm.set(levels.get(0));
                dispose();
            }
            if(level2Button.contains(touchX,touchY)){
                this.gsm.set(levels.get(1));
                dispose();
            }
            if(level3Button.contains(touchX,touchY)){
                this.gsm.set(levels.get(2));
                dispose();
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
