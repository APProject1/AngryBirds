package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.badlogic.AngryBirds.Elements.Bird;
import io.github.badlogic.AngryBirds.Elements.Block;
import io.github.badlogic.AngryBirds.Elements.Pig;

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
    int level;

    public Level(GameStateManager gsm,int num,ArrayList<Bird> birds,ArrayList<Pig> pigs,ArrayList<Block> blocks,String texture) {
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
        sb.begin();
        sb.draw(background,0,-60,1200,800);
        sb.draw(pauseButtonTexture,790,510);
        sb.draw(slingright,160,130,40,160);
        for (Bird bird:birds){
            sb.draw(bird.texture,bird.x,bird.y,bird.width,bird.height);
        }
        for (Block block:blocks){
            sb.draw(block.texture,block.x,block.y,block.width,block.height);
        }
        for (Pig pig:pigs){
            sb.draw(pig.texture,pig.x,pig.y,pig.width,pig.height);
        }
        sb.draw(slingleft,133,200,40,100);

        sb.end();

    }


    public void dispose() {

    }}
