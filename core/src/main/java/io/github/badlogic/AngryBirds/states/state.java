package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class state {
    protected OrthographicCamera cam;
    protected ExtendViewport viewport;
    protected Vector3 pointer;
    public GameStateManager gsm;

    public state(GameStateManager gsm){
        cam=new OrthographicCamera();
        //viewport=new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),cam);
        //cam.setToOrtho(false,900,600);
        pointer=new Vector3();
        this.gsm=gsm;
    }
    public void resize(int width,int height) {
        viewport.update(width,height);
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
