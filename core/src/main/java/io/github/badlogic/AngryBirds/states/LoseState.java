package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoseState extends state{
    private Texture background;
    private BitmapFont font1;
    private BitmapFont font2;
    int level;
    private Stage stage;
    private TextButton.TextButtonStyle textButtonStyle;
    public List<FallingObject> fallingObjects;


    public LoseState(GameStateManager gsm,int level){
        super(gsm);
        Music flymusic = Gdx.audio.newMusic(Gdx.files.internal("fail.mp3"));
        flymusic.setVolume(1.5f); // Set volume
        flymusic.play();
        background= new Texture("bg.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
        font2=new BitmapFont(Gdx.files.internal("font3.fnt"));
        this.level=level;
        fallingObjects = new ArrayList<>();
        Texture f1 = new Texture("feather1.png");
        Texture f2= new Texture("feather2.png");
        Texture f3 = new Texture("feather3.png");
        Texture f4 = new Texture("feather4.png");
        Texture[] textures ={f1,f2,f3,f4};
        for (int i = 0; i <50; i++) {
            Texture texture = textures[(int) (Math.random() * textures.length)];
            float x = (i * 100) % 800; // Cycle through screen width with spacing of 150
            float y = 600 + (i / 5) * 100;
            FallingObject obj=new FallingObject(texture, (float) Math.random() * 900, (float)Math.random()*600, 50 + (float) Math.random() * 50);
            obj.width=10;
            obj.height=30;
            fallingObjects.add(obj);
        }

        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font=font1;

        font1.getData().setScale(0.5f);
        TextButton homeButton=new TextButton("Go To Home",textButtonStyle);
        homeButton.setPosition(330,340);

        TextButton replayButton=new TextButton("Replay",textButtonStyle);
        replayButton.setPosition(320,280);

        TextButton exitButton=new TextButton("Exit",textButtonStyle);
        exitButton.setPosition(310,220);

        stage.addActor(homeButton);
        stage.addActor(replayButton);
        stage.addActor(exitButton);

        homeButton.addListener(event->{
            if(homeButton.isPressed()){
                this.gsm.set(new MenuState(gsm));
                dispose();
                return true;
            }
            return false;
        });

        replayButton.addListener(event->{
            if(replayButton.isPressed()){
                if (level==1){
                    this.gsm.set(LevelSelectState.level1setup(gsm));}
                if (level==2){
                    this.gsm.set(LevelSelectState.level2setup(gsm));}
                if (level==3){
                    this.gsm.set(LevelSelectState.level3setup(gsm));}
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
        for (FallingObject obj : fallingObjects) {
            obj.render(sb);
        }
        font1.getData().setScale(0.8f);
        font2.getData().setScale(1.2f);
        font2.draw(sb,"Too Bad! :(",240,500);
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
