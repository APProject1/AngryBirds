package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class SavedGamesState extends state{
    private Texture background;
    //private ShapeRenderer shape;
    private BitmapFont font1;
    private BitmapFont font2;
    private Texture back;
    private Rectangle backButton;
    private Rectangle save1;
    private Rectangle save2;
    private Rectangle save3;

    public SavedGamesState(GameStateManager gsm){
        super(gsm);
        background= new Texture("bg2.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
        font2=new BitmapFont(Gdx.files.internal("font3.fnt"));
        back=new Texture("backbutton.png");
        backButton=new Rectangle(50,500,50,50);
        save1=new Rectangle(320,370,260,40);
        save2=new Rectangle(320,300,260,40);
        save3=new Rectangle(320,230,260,40);
        //shape=new ShapeRenderer();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.graphics.getHeight() - Gdx.input.getY();
            if(backButton.contains(touchX,touchY)){
                this.gsm.set(new MenuState(gsm));
                dispose();
            }
            if(save1.contains(touchX,touchY)){
                this.gsm.set(new LoadSavedGameState(gsm));
                dispose();
            }
            if(save2.contains(touchX,touchY)){
                this.gsm.set(new LoadSavedGameState(gsm));
                dispose();
            }
            if(save3.contains(touchX,touchY)){
                this.gsm.set(new LoadSavedGameState(gsm));
                dispose();
            }
    }}

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,900,600);
        sb.draw(back,50,500,50,50);
        font1.getData().setScale(1.2f);
        font1.draw(sb,"SAVED GAMES",240,540);
        font2.getData().setScale(0.7f);
        font2.draw(sb,"Saved game 1",320,400);
        font2.draw(sb,"Saved game 2",320,330);
        font2.draw(sb,"Saved game 3",320,260);
        sb.end();

        //shape.begin(ShapeRenderer.ShapeType.Line);
        //shape.setColor(1, 0, 0, 1);
        //shape.rect(save3.x, save3.y, save3.width, save3.height);
        //shape.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        back.dispose();
        font1.dispose();
        font2.dispose();
        //shape.dispose();
    }
}
