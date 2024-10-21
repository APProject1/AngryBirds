package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class MenuState extends state{
    private Texture background;
    private Texture playButtonTexture;
    private Rectangle playButton;
    private Texture saveButton;
    private Texture logo;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background= new Texture("bg.png");
        playButtonTexture =new Texture("playbutton.png");
        saveButton=new Texture("savebtn.png");
        logo=new Texture("logo1.png");
        playButton= new Rectangle(450-(playButtonTexture.getWidth()/2),250,50,50);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.input.getY();
            if(playButton.contains(touchX,touchY)) {
                this.gsm.set(new PlayState(this.gsm));
                dispose();
            }
        }
    }

    @Override
    public void update(float dt){
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,900,600);
        sb.draw(logo,250,450,400,100);
        sb.draw(playButtonTexture,450-(playButtonTexture.getWidth()/2),300);
        sb.draw(saveButton,450-(saveButton.getWidth()/2),200, playButtonTexture.getWidth(), playButtonTexture.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        logo.dispose();
        playButtonTexture.dispose();
        saveButton.dispose();
    }
}
