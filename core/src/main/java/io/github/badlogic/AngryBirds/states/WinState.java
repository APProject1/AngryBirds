package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WinState extends state{
    private Texture background;
    private Texture congrats;
    private BitmapFont font1;
    int level;
    private Stage stage;
    private TextButton.TextButtonStyle textButtonStyle;
    public List<FallingObject> fallingObjects;



    public WinState(GameStateManager gsm,int level){
        super(gsm);
        Music flymusic = Gdx.audio.newMusic(Gdx.files.internal("win.mp3"));
        flymusic.setVolume(1.5f); // Set volume
        flymusic.play();
        background= new Texture("bg.png");
        congrats=new Texture("congrats.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
        this.level=level;
        fallingObjects = new ArrayList<>();
        Texture pig1 = new Texture("medium1.png");
        Texture pig4 = new Texture("king1.png");
        Texture pig2 = new Texture("king2.png");
        Texture pig3 = new Texture("dying.png");
        Texture[] textures ={pig1,pig2,pig3,pig4};
        for (int i = 0; i <10; i++) {
            Texture texture = textures[(int) (Math.random() * textures.length)];
            float x = (i * 100) % 800; // Cycle through screen width with spacing of 150
            float y = 600 + (i / 5) * 100;
            fallingObjects.add(new FallingObject(texture, (float) Math.random() * 900, (float)Math.random()*600, 50 + (float) Math.random() * 50));
        }

        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font=font1;

        font1.getData().setScale(0.5f);
        TextButton homeButton=new TextButton("Go To Home",textButtonStyle);
        homeButton.setPosition(340,280);

        TextButton nextLevelButton=new TextButton("Next Level",textButtonStyle);
        nextLevelButton.setPosition(340,220);

        TextButton exitButton=new TextButton("Exit",textButtonStyle);
        exitButton.setPosition(310,160);

        stage.addActor(homeButton);
        stage.addActor(nextLevelButton);
        stage.addActor(exitButton);

        homeButton.addListener(event->{
            if(homeButton.isPressed()){
                this.gsm.set(new MenuState(gsm));
                dispose();
                return true;
            }
            return false;
        });

        nextLevelButton.addListener(event->{
            if(nextLevelButton.isPressed()){
                if (level==1){
                    this.gsm.set(LevelSelectState.level2setup(gsm));}
                if (level==2){
                    this.gsm.set(LevelSelectState.level3setup(gsm));}
                if (level==3){
                    this.gsm.set(new MenuState(gsm));}
                dispose();
                return true;
            }
            return false;
        });

        exitButton.addListener(event->{
            if(exitButton.isPressed()){
                Gdx.app.exit();
                dispose();
                return true;
            }
            return false;
        });
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        stage.act(dt);
        for (FallingObject obj : fallingObjects){
            obj.update(dt);
            if(obj.isOnGround) {
                float newX = (float) (Math.random() * 800);
                obj.reset(newX);
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,900,600);
        font1.getData().setScale(0.8f);
        for (FallingObject obj : fallingObjects) {
            obj.render(sb);
        }
        sb.draw(congrats,250,380,400,150);
        sb.end();
        stage.draw();
    }
    @Override
    public void dispose() {
        background.dispose();
        for (FallingObject obj : fallingObjects) {
            obj.getTexture().dispose();
        }
        font1.dispose();
        stage.dispose();
    }
}
