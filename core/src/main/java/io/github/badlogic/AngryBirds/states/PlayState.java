package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayState extends state{
    private Texture bird;
    private Texture background;
    private Texture pauseButtonTexture;
    private Rectangle pauseButton;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        background= new Texture("bg.png");
        bird=new Texture("redbird.png");
        pauseButtonTexture=new Texture("pausebtn.png");
        pauseButton= new Rectangle(790,40,60,50);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.input.getY();
            if(pauseButton.contains(touchX,touchY)) {
                this.gsm.set(new PauseState(this.gsm));
                dispose();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,900,600);
        sb.draw(bird,80,80, 50,50);
        sb.draw(pauseButtonTexture,790,510);
        sb.end();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.gsm.set(new WinState(this.gsm));;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            this.gsm.set(new LoseState(this.gsm));;
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        pauseButtonTexture.dispose();
    }
}
