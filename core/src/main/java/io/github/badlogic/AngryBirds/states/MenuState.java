package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import java.util.List;
import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuState extends state{
    private Texture background;
    private Texture logo;
    private Stage stage;
    private Button playButton;
    private Button savedBtn;
    private Button exitBtn;
    private Texture playButtonTexture;
    public List<FallingObject> fallingObjects;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background= new Texture("bg.png");
        playButtonTexture=new Texture("playbutton.png");
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        playButton= createButton("playbutton.png", 450-(playButtonTexture.getWidth()/2),330,playButtonTexture.getWidth(), playButtonTexture.getHeight());
        savedBtn= createButton("savebtn.png", 450-(playButtonTexture.getWidth()/2),240, playButtonTexture.getWidth(), playButtonTexture.getHeight());
        exitBtn=createButton("exit.png", 450-(playButtonTexture.getWidth()/2),150, playButtonTexture.getWidth(), playButtonTexture.getHeight());
        logo=new Texture("logo1.png");

        fallingObjects = new ArrayList<>();
        Texture bird1 = new Texture("blackbird.png");
        Texture pig1 = new Texture("kingpig.png");
        Texture bird2 = new Texture("redbird1.png");
        Texture bird3 = new Texture("yellowbird.png");
        Texture pig2 = new Texture("smallpig.png");
        Texture pig3 = new Texture("mediumpig.png");
        Texture bird4 = new Texture("redbird1.png");
        Texture[] textures = {bird1, bird2, bird3, pig1, pig2, pig3,bird4};

        for (int i = 0; i <10; i++) {
            Texture texture = textures[(int) (Math.random() * textures.length)];
            float x = (i * 100) % 800; // Cycle through screen width with spacing of 150
            float y = 600 + (i / 5) * 100;
            fallingObjects.add(new FallingObject(texture, (float) Math.random() * 800, 600, 50 + (float) Math.random() * 50));
        }

        stage.addActor(playButton);
        stage.addActor(savedBtn);
        stage.addActor(exitBtn);

        playButton.addListener(event->{
            if(playButton.isPressed()){
                this.gsm.set(new LevelSelectState(this.gsm));
                dispose();
                return true;
            }
            return false;
        });
        savedBtn.addListener(event->{
            if(savedBtn.isPressed()){
                this.gsm.set(new SavedGamesState(this.gsm));
                dispose();
                return true;
            }
            return false;
        });
        exitBtn.addListener(event->{
            if(exitBtn.isPressed()){
                Gdx.app.exit();
                dispose();
                return true;
            }
            return false;
        });
    }

    @Override
    public void handleInput(){
    }

    private Button createButton(String texturePath, float x, float y, float width, float height) {
        Texture texture = new Texture(texturePath);
        TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
        Button button = new Button(drawable);
        button.setBounds(x, y, width, height);
        return button;
    }

    @Override
    public void resize(int width,int height){
        stage.getViewport().update(width,height, true);
    }

    @Override
    public void update(float dt){
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
        sb.draw(logo,250,450,400,100);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        logo.dispose();
        for (FallingObject obj : fallingObjects) {
            obj.getTexture().dispose();
        }
        stage.dispose();
    }
}
