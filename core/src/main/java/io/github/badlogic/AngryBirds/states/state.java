package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class state {
    protected OrthographicCamera cam;
    protected Vector3 pointer;
    public GameStateManager gsm;

    public state(GameStateManager gsm){
        cam=new OrthographicCamera();
        pointer=new Vector3();
        this.gsm=gsm;
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
