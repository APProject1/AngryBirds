package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.w3c.dom.Text;

import java.awt.*;

public class WinState extends state{
    private Texture background;
    private Texture congrats;
    private Rectangle homeButton;
    private Rectangle exitButton;
    private Rectangle nextLevelButton;
    //private ShapeRenderer shape;
    private BitmapFont font1;
    int level;


    public WinState(GameStateManager gsm,int level){
        super(gsm);
        background= new Texture("bg.png");
        congrats=new Texture("congrats.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
        homeButton=new Rectangle(300,300,250,50);
        exitButton=new Rectangle(300,180,100,50);
        nextLevelButton=new Rectangle(300,240,300,50);
        this.level=level;
        //shape=new ShapeRenderer();
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
            if(nextLevelButton.contains(touchX,touchY)) {
                if (level==1){
                    this.gsm.set(LevelSelectState.level2setup(gsm));}
                if (level==2){
                    this.gsm.set(LevelSelectState.level3setup(gsm));}
                if (level==3){
                    this.gsm.set(new MenuState(gsm));}
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
        sb.draw(congrats,250,380,400,150);
        font1.draw(sb,"Go to Home",300,340);
        font1.draw(sb,"Next Level",300,280);
        font1.draw(sb,"Exit",300,220);
        sb.end();
        /*shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(1, 0, 0, 1);
        shape.rect(exitButton.x,exitButton.y, exitButton.width, exitButton.height);
        shape.end();*/
    }



    @Override
    public void dispose() {
        background.dispose();
        font1.dispose();
    }
}

