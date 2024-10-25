package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class MenuState extends state{
    private Texture background;
    private Texture playButtonTexture;
    private Rectangle playButton;
    private Texture saveButtonText;
    private Rectangle savedBtn;
    private Texture logo;
    //private ShapeRenderer shape;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background= new Texture("bg.png");
        playButtonTexture =new Texture("playbutton.png");
        saveButtonText =new Texture("savebtn.png");
        logo=new Texture("logo1.png");
        playButton= new Rectangle(450-(playButtonTexture.getWidth()/2),250,50,50);
        savedBtn=new Rectangle(450-(saveButtonText.getWidth()/2),350, playButtonTexture.getWidth(), playButtonTexture.getHeight());
        //shape=new ShapeRenderer();

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.input.getY();
            if(playButton.contains(touchX,touchY)){
                this.gsm.set(new LevelSelectState(this.gsm));
                dispose();
            }else if(savedBtn.contains(touchX,touchY)){
                this.gsm.set(new SavedGamesState(this.gsm));
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
        sb.draw(saveButtonText,450-(saveButtonText.getWidth()/2),200, playButtonTexture.getWidth(), playButtonTexture.getHeight());
        sb.end();

        //shape.begin(ShapeRenderer.ShapeType.Line);
        //shape.setColor(1, 0, 0, 1);
        //shape.rect(savedBtn.x, savedBtn.y, savedBtn.width, savedBtn.height);
        //shape.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        logo.dispose();
        playButtonTexture.dispose();
        saveButtonText.dispose();
    }
}
