package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SavedGamesState extends state{
    private Texture background;
    private ShapeRenderer shape;
    private BitmapFont font1;

    public SavedGamesState(GameStateManager gsm){
        super(gsm);
        background= new Texture("bg2.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,900,600);
        font1.getData().setScale(1.2f);
        font1.draw(sb,"SAVED GAMES",240,540);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        font1.dispose();
    }
}
