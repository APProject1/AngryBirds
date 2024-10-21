package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseState extends state{
    private Texture background;

    public PauseState(GameStateManager gsm){
        super(gsm);
        background= new Texture("bg.png");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,900,600);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
