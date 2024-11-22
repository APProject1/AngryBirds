package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.io.*;
import java.util.*;

public class SavedGamesState extends state{
    private Texture background;
    private BitmapFont font1;
    private BitmapFont font2;
    private Stage stage;
    private Button backButton;
    private TextButton.TextButtonStyle textButtonStyle;
    private List<String> saveFiles;

    public SavedGamesState(GameStateManager gsm){
        super(gsm);
        background = new Texture("bg2.png");
        font1=new BitmapFont(Gdx.files.internal("font3.fnt"));
        font2=new BitmapFont(Gdx.files.internal("font3.fnt"));

        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        backButton = createButton("backbutton.png", 50, 500, 50, 50);
        /*textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font=font2;

        font2.getData().setScale(0.7f);
        TextButton save1Button=new TextButton("Saved Game 1",textButtonStyle);
        save1Button.setPosition(320,370);
        save1Button.setSize(260,40);

        TextButton save2Button=new TextButton("Saved Game 2",textButtonStyle);
        save2Button.setPosition(320,300);
        save2Button.setSize(260,40);

        TextButton save3Button=new TextButton("Saved Game 3",textButtonStyle);
        save3Button.setPosition(320,230);
        save3Button.setSize(260,40);*/

        stage.addActor(backButton);
        //stage.addActor(save1Button);
        //stage.addActor(save2Button);
        //stage.addActor(save3Button);

        backButton.addListener(event->{
            if(backButton.isPressed()){
                this.gsm.set(new MenuState(gsm));
                dispose();
                return true;
            }
            return false;
        });
        createTimestampButtons("saves/birdPositions.txt",stage);
        /*save1Button.addListener(event -> {
            if (save1Button.isPressed()) {
                this.gsm.set(new LoadSavedGameState(gsm));
                dispose();
                return true;
            }
            return false;
        });

        save2Button.addListener(event -> {
            if (save2Button.isPressed()) {
                this.gsm.set(new LoadSavedGameState(gsm));
                dispose();
                return true;
            }
            return false;
        });

        save3Button.addListener(event -> {
            if (save3Button.isPressed()) {
                this.gsm.set(new LoadSavedGameState(gsm));
                dispose();
                return true;
            }
            return false;
        });*/

        /*saveFiles=getSavedGames();
        VerticalGroup saveList=new VerticalGroup();
        saveList.space(15);
        saveList.setPosition(450,400);
        stage.addActor(saveList);

        for(String fileName:saveFiles){
            TextButton.TextButtonStyle style=new TextButton.TextButtonStyle();
            style.font=font2;
            font2.getData().setScale(0.5f);

            TextButton saveButton=new TextButton(fileName,style);
            saveButton.addListener(event->{
                if(saveButton.isPressed()){
                    return true;
                }
                return false;
            });
            saveList.addActor(saveButton);
        }*/

        /*Map<String, String> gameIDs=getUniqueGameIDs("birdsPosition.txt");
        VerticalGroup saveList=new VerticalGroup();
        saveList.space(15);
        saveList.setPosition(400, 400);
        stage.addActor(saveList);

        for (Map.Entry<String, String> entry : gameIDs.entrySet()){
            String gameId = entry.getKey();
            String level = entry.getValue();

            TextButton.TextButtonStyle style=new TextButton.TextButtonStyle();
            style.font = font2;
            font2.getData().setScale(0.5f);

            TextButton saveButton = new TextButton("Game ID: " + gameId + " (Level " + level + ")", style);
            saveButton.addListener(event -> {
                if(saveButton.isPressed()) {
                    //loadGame(gameId);
                    return true;
                }
                return false;
            });
            saveList.addActor(saveButton);
        }
    }

    private Map<String, String> getUniqueGameIDs(String fileName){
        Map<String, String> gameIDs=new LinkedHashMap<>();
        try (BufferedReader reader=new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(", ")){
                    String[] parts = line.split(", ");
                    if (parts.length >= 2) {
                        String gameId = parts[0];
                        String level = parts[1];
                        gameIDs.putIfAbsent(gameId,level);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameIDs;
    }/*


    /*public List<String> getSavedGames() {
        List<String> saveFiles=new ArrayList<>();
        File saveDir=new File("saves");
        if(saveDir.exists() && saveDir.isDirectory()){
            File[] files=saveDir.listFiles();
            if(files!=null){
                for(File file:files){
                    saveFiles.add(file.getName());
                }
            }
        }
        return saveFiles;
    }*/

    /*public void loadLevel(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/" + fileName))) {
            Level loadedLevel = (Level) ois.readObject();
            loadedLevel.reinitializeTransientFields();
            System.out.println("Game loaded successfully: " + fileName);
            this.gsm.set(loadedLevel);
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }*/
    }


        public void createTimestampButtons(String fileName, Stage stage){
            Set<String> uniqueTimestamps = new HashSet<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null){
                    if (!line.isEmpty()) {
                        String[] parts = line.split(", ");
                        if (parts.length > 0) {
                            uniqueTimestamps.add(parts[0].trim());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            float yPosition = 500;
            float buttonHeight = 50;
            TextButton.TextButtonStyle style=new TextButton.TextButtonStyle();
            style.font=font2;
            font2.getData().setScale(0.5f);
            for (String timestamp : uniqueTimestamps) {
                TextButton button = new TextButton(timestamp,style);
                button.setPosition(100, yPosition);
                button.setSize(200, buttonHeight);
                stage.addActor(button);

                // Add a click listener to handle button interaction
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("Timestamp selected: " + timestamp);
                        //loadBirdPositions(fileName, timestamp);
                    }
                });
                yPosition -= buttonHeight + 10; // Move to the next position for the next button
            }
        }

        @Override
    public void resize(int width,int height){
        stage.getViewport().update(width,height, true);
    }

    private Button createButton(String texturePath, float x, float y, float width, float height) {
        Texture texture = new Texture(texturePath);
        TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
        Button button = new Button(drawable);
        button.setBounds(x, y, width, height);
        return button;
    }

    @Override
    protected void handleInput() {
        // Input is handled by the stage and button listeners
    }

    @Override
    public void update(float dt){
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb){
        sb.begin();
        sb.draw(background, 0, 0, 900, 600);
        font1.getData().setScale(1.2f);
        font1.draw(sb, "SAVED GAMES", 240, 540);
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
