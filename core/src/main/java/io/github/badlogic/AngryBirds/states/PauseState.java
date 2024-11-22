package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.io.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PauseState extends state{
    private Texture background;
    private BitmapFont font1;
    private BitmapFont font2;
    private Texture continuebtnText;
    private Texture saveBtnText;
    private Texture homeBtnText;
    /*private Rectangle continueBtn;
    private Rectangle saveBtn;
    private Rectangle homeBtn;*/
    Level level;
    private Stage stage;
    private TextButton.TextButtonStyle textButtonStyle;

    public PauseState(GameStateManager gsm, Level level){
        super(gsm);
        background= new Texture("bg.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
        font2=new BitmapFont(Gdx.files.internal("font3.fnt"));
        continuebtnText= new Texture("play1btn.png");
        saveBtnText =new Texture("savebtn.png");
        homeBtnText =new Texture("home.png");
        /*continueBtn=new Rectangle(290,310,310,40);
        saveBtn=new Rectangle(290,250,200,45);
        homeBtn=new Rectangle(350,200,80,40);*/
        this.level=level;

        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        textButtonStyle=new TextButton.TextButtonStyle();
        textButtonStyle.font=font1;
        font1.getData().setScale(0.5f);
        TextButton continueBtn=new TextButton("Continue Playing",textButtonStyle);
        continueBtn.setPosition(350,320);

        TextButton saveBtn=new TextButton("Save Game",textButtonStyle);
        saveBtn.setPosition(350,260);

        TextButton homeBtn=new TextButton("Go To Home",textButtonStyle);
        homeBtn.setPosition(350,200);

        stage.addActor(saveBtn);
        stage.addActor(continueBtn);
        stage.addActor(homeBtn);

        continueBtn.addListener(event->{
            if(continueBtn.isPressed()){
                gsm.pop();
                dispose();
                return true;
            }
            return false;
        });

        saveBtn.addListener(event->{
            if(saveBtn.isPressed()){
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                level.saveBirdPositions("saves/birdPositions.txt",timestamp);
                level.saveBlockPositions("saves/blockPositions.txt",timestamp);
                level.savePigPositions("saves/pigPositions.txt",timestamp);
                this.gsm.set(new SavedGamesState(gsm));
                dispose();
                return true;
            }
            return false;
        });

        homeBtn.addListener(event->{
            if(homeBtn.isPressed()){
                this.gsm.set(new MenuState(gsm));
                dispose();
                return true;
            }
            return false;
        });
    }

    public void saveLevel(){
        try {
            File saveDir=new File("saves");
            if(!saveDir.exists()){
                saveDir.mkdirs();}
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String time=currentDateTime.format(formatter);
            String fileName="saves/game_"+time;

            // Save the Level instance
            try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fileName))){
                oos.writeObject(level);
                System.out.println("Game saved successfully to: "+fileName);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,900,600);
        sb.draw(continuebtnText,290,310,45,45);
        sb.draw(saveBtnText,290,250,45,45);
        sb.draw(homeBtnText,290,190,45,45);
        font2.getData().setScale(1.2f);
        font2.draw(sb,"GAME PAUSED",240,500);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        font1.dispose();
        font2.dispose();
        stage.dispose();
    }
}
