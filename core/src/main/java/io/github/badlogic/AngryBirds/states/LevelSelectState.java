package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class LevelSelectState extends state{
    private Texture background;
    private Texture level1;
    private Texture level2;
    private Texture level3;
    private Rectangle level1Button;
    private Rectangle level2Button;
    private Rectangle level3Button;
    private ShapeRenderer shape;
    private BitmapFont font1;

    public LevelSelectState(GameStateManager gsm){
        super(gsm);
        level1=new Texture("level1.png");
        level2=new Texture("level2.png");
        level3=new Texture("level3.png");
        level1Button=new Rectangle(130,220,150,120);
        level2Button=new Rectangle(380,220,150,120);
        level3Button=new Rectangle(630,220,150,120);

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
        sb.draw(level1,130,220,150,120);
        sb.draw(level2,380,220,150,120);
        sb.draw(level3,630,220,150,120);
        font1.getData().setScale(1.2f);
        font1.draw(sb,"CHOOSE LEVEL",230,540);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        font1.dispose();
    }
}
