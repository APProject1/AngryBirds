package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.w3c.dom.Text;

import java.awt.*;

public class LoseState extends state{
    private Texture background;
    private Rectangle homeButton;
    private Rectangle replayButton;
    private Rectangle exitButton;
    //private ShapeRenderer shape;
    private BitmapFont font1;
    private BitmapFont font2;
    int level;


    public LoseState(GameStateManager gsm,int level){
        super(gsm);
        background= new Texture("bg.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
        homeButton=new Rectangle(300,300,250,50);
        exitButton=new Rectangle(300,180,100,50);
        replayButton=new Rectangle(300,240,150,50);
        //shape=new ShapeRenderer();
        font2=new BitmapFont(Gdx.files.internal("font3.fnt"));
        this.level=level;

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.graphics.getHeight()-Gdx.input.getY();
            System.out.println("Touch coordinates: " + touchX + ", " + touchY);
            if(homeButton.contains(touchX,touchY)) {
                this.gsm.set(new MenuState(this.gsm));
                dispose();
            }
            if(replayButton.contains(touchX,touchY)) {
                if (level==1){
                    this.gsm.set(LevelSelectState.level1setup(gsm));}
                if (level==2){
                    this.gsm.set(LevelSelectState.level2setup(gsm));}
                if (level==3){
                    this.gsm.set(LevelSelectState.level3setup(gsm));}
                dispose();
            }
            if(exitButton.contains(touchX,touchY)) {
                Gdx.app.exit();
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
        font1.getData().setScale(0.8f);
        font2.getData().setScale(1.2f);
        font2.draw(sb,"Too Bad! :(",240,500);
        font1.draw(sb,"Go to Home",300,340);
        font1.draw(sb,"Replay",300,280);
        font1.draw(sb,"Exit",300,220);
        sb.end();
        /*shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(1, 0, 0, 1);
        shape.rect(replayButton.x,replayButton.y, replayButton.width, replayButton.height);
        shape.end();*/
    }


    @Override
    public void dispose() {
        background.dispose();
        font1.dispose();
    }
}

