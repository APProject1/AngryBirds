package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.awt.*;

public class PauseState extends state{
    private Texture background;
    private BitmapFont font1;
    private BitmapFont font2;
    private Texture continuebtnText;
    private Texture saveBtnText;
    private Texture homeBtnText;
    private Rectangle continueBtn;
    private Rectangle saveBtn;
    private Rectangle homeBtn;
    Level level;
    //private ShapeRenderer shape;

    public PauseState(GameStateManager gsm, Level level){
        super(gsm);
        background= new Texture("bg.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
        font2=new BitmapFont(Gdx.files.internal("font3.fnt"));
        continuebtnText= new Texture("play1btn.png");
        saveBtnText =new Texture("savebtn.png");
        homeBtnText =new Texture("home.png");
        continueBtn=new Rectangle(290,310,310,40);
        saveBtn=new Rectangle(290,250,200,45);
        homeBtn=new Rectangle(350,200,80,40);
        this.level=level;
        //shape=new ShapeRenderer();

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            float touchX=Gdx.input.getX();
            float touchY=Gdx.graphics.getHeight() - Gdx.input.getY();
            if(continueBtn.contains(touchX,touchY)) {
                this.gsm.set(level);
                dispose();
            }
            if(saveBtn.contains(touchX,touchY)){
                this.gsm.set(new SavedGamesState(gsm));
            }
            if(homeBtn.contains(touchX,touchY)){
                this.gsm.set(new MenuState(gsm));
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
        sb.draw(continuebtnText,290,310,45,45);
        sb.draw(saveBtnText,290,250,45,45);
        sb.draw(homeBtnText,290,190,45,45);
        font1.getData().setScale(0.5f);
        font2.getData().setScale(1.2f);
        font1.draw(sb,"Continue Playing",350,340);
        font1.draw(sb,"Save Game",350,280);
        font2.draw(sb,"GAME PAUSED",240,500);
        font1.draw(sb,"Go To Home",350,220);
        sb.end();


        //shape.begin(ShapeRenderer.ShapeType.Line);
        //shape.setColor(1, 0, 0, 1);
        //shape.rect(continueBtn.x, continueBtn.y, continueBtn.width, continueBtn.height);
        //shape.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        font1.dispose();
        font2.dispose();
    }
}
