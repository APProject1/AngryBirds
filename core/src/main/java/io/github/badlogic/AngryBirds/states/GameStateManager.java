package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private Stack<state> states;

    public GameStateManager() {
        states = new Stack<state>();
        Music theme = Gdx.audio.newMusic(Gdx.files.internal("angry.mp3"));
        theme.setLooping(true); // Enable looping
        theme.setVolume(0.4f); // Set volume
        theme.play();
    }

    public void push(state state) {
        states.push(state);
    }

    public void pop() {
        states.pop();
    }

    public void set(state state) {
        if (!states.isEmpty()) {
            states.pop();
        }
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

    public void resize(int width, int height) {
        if (!states.isEmpty()) {
            states.peek().resize(width, height);
        }
    }
}
