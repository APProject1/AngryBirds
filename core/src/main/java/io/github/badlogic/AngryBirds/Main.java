package io.github.badlogic.AngryBirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.badlogic.AngryBirds.states.GameStateManager;
import io.github.badlogic.AngryBirds.states.MenuState;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private GameStateManager gsm;
    private SpriteBatch batch;


    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm=new GameStateManager();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void render() {
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

